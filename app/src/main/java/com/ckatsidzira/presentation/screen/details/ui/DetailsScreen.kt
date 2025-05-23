package com.ckatsidzira.presentation.screen.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ckatsidzira.BuildConfig
import com.ckatsidzira.R
import com.ckatsidzira.presentation.custom.favorite.FavoriteButton
import com.ckatsidzira.presentation.custom.favorite.FavoriteState
import com.ckatsidzira.presentation.custom.state.ShowError
import com.ckatsidzira.presentation.custom.state.ShowLoading
import com.ckatsidzira.presentation.custom.state.ShowOffline
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.screen.details.DetailsUiAction
import com.ckatsidzira.presentation.screen.details.DetailsUiState
import com.ckatsidzira.presentation.screen.details.DetailsViewModel
import com.ckatsidzira.presentation.screen.details.ScreenData

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailsLayout(
        modifier = modifier,
        state = state,
        onAction = viewModel::handleAction,
    )
}

@Composable
fun DetailsLayout(
    modifier: Modifier = Modifier,
    state: DetailsUiState,
    onAction: (DetailsUiAction) -> Unit,
) {
    when (state.screenData) {
        ScreenData.Initial -> Unit
        ScreenData.Loading -> ShowLoading()
        ScreenData.Offline -> ShowOffline()
        is ScreenData.Error -> ShowError(
            message = state.screenData.message
        )

        is ScreenData.Data -> DetailsContent(
            modifier = modifier,
            movie = state.screenData.movie,
            favoriteState = state.screenData.favoriteState,
            onAction = onAction,
        )
    }
}

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    movie: MovieUiModel,
    favoriteState: FavoriteState,
    onAction: (DetailsUiAction) -> Unit,
) {
    var isFavorited by rememberSaveable { mutableStateOf(favoriteState == FavoriteState.Favorite) }
    var toggling by rememberSaveable { mutableStateOf(false) }
    val favoriteUpdate = remember {
        { movie: MovieUiModel, favorited: Boolean ->
            onAction(
                DetailsUiAction.OnUpdateFavorites(
                    movie,
                    favorited
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.IMAGES_BASE_URL + movie.posterPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
            )

            FavoriteButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                state = when {
                    toggling -> FavoriteState.Toggling
                    isFavorited -> FavoriteState.Favorite
                    else -> FavoriteState.NotFavorite
                },
                onFavoriteClicked = {
                    toggling = true
                    favoriteUpdate(movie, isFavorited)
                    toggling = false
                    isFavorited = !isFavorited
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 22.sp
        )
    }
}
package com.ckatsidzira.presentation.screen.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    var isFavorited by rememberSaveable { mutableStateOf(favoriteState == FavoriteState.Favorited) }
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

    Column(modifier = modifier) {
        Text(text = movie.title)

        FavoriteButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp),
            state = when {
                toggling -> FavoriteState.Toggling
                isFavorited -> FavoriteState.Favorited
                else -> FavoriteState.NotFavorited
            },
            onFavoriteClicked = {
                toggling = true
                favoriteUpdate(movie, isFavorited)
                toggling = false
                isFavorited = !isFavorited
            }
        )
    }
}
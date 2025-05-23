package com.ckatsidzira.presentation.screen.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ckatsidzira.presentation.custom.BookmarkButton
import com.ckatsidzira.presentation.custom.BookmarkState
import com.ckatsidzira.presentation.custom.ScreenSection
import com.ckatsidzira.presentation.custom.states.ShowError
import com.ckatsidzira.presentation.custom.states.ShowLoading
import com.ckatsidzira.presentation.custom.states.ShowOffline
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.screen.home.HomeUiAction
import com.ckatsidzira.presentation.screen.home.HomeUiState
import com.ckatsidzira.presentation.screen.home.HomeViewModel
import com.ckatsidzira.presentation.screen.home.ScreenData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeLayout(
        modifier = modifier,
        state = state,
        onAction = viewModel::handleAction
    )
}

@Composable
fun HomeLayout(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
) {
    when (state.screenData) {
        ScreenData.Initial -> Unit
        ScreenData.Loading -> ShowLoading()
        ScreenData.Offline -> ShowOffline()
        is ScreenData.Error -> ShowError(
            message = state.screenData.message
        )

        is ScreenData.Data -> HomeContent(
            modifier = modifier,
            movies = state.screenData.trending,
            favorites = state.screenData.favorites,
            selectedDayIndex = state.screenData.selectedTimeWindowIndex,
            onAction = onAction,
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    movies: List<MovieUiModel>,
    favorites: List<MovieUiModel>,
    selectedDayIndex: Int,
    onAction: (HomeUiAction) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        ScreenSection(
            title = "Trending",
            defaultSelectedItemIndex = selectedDayIndex,
            onSelectedChanged = { name, index ->
                onAction(HomeUiAction.OnTimeWindowChanged(name, index))
            }
        )

        LazyColumn {
            items(
                items = movies,
                key = { it.id }
            ) { movie ->
                Text(text = movie.title)

                val isFavorite by rememberSaveable { mutableStateOf(favorites.find { it.id == movie.id } != null) }

                println(isFavorite)
            }
        }
    }
}
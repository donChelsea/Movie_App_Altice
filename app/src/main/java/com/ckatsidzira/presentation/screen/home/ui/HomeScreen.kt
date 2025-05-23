package com.ckatsidzira.presentation.screen.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ckatsidzira.presentation.custom.section.ScreenSection
import com.ckatsidzira.presentation.custom.state.ShowError
import com.ckatsidzira.presentation.custom.state.ShowLoading
import com.ckatsidzira.presentation.custom.state.ShowOffline
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.navigation.Screen
import com.ckatsidzira.presentation.screen.home.HomeUiAction
import com.ckatsidzira.presentation.screen.home.HomeUiEvent
import com.ckatsidzira.presentation.screen.home.HomeUiState
import com.ckatsidzira.presentation.screen.home.HomeViewModel
import com.ckatsidzira.presentation.screen.home.ScreenData

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeUiEvent.OnMovieClicked -> navController.navigate(
                    Screen.Details.withArgs(
                        event.id
                    )
                )
            }
        }
    }

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
            selectedDayIndex = state.screenData.selectedTimeWindowIndex,
            onAction = onAction,
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    movies: List<MovieUiModel>,
    selectedDayIndex: Int,
    onAction: (HomeUiAction) -> Unit,
) {
    val selectedItemIndexUpdate = remember {
        { name: String, index: Int ->
            onAction(HomeUiAction.OnTimeWindowChanged(name, index))
        }
    }
    val movieDetailsUpdate = remember { { id: Int -> onAction(HomeUiAction.OnMovieClicked(id)) } }

    Column(
        modifier = modifier,
    ) {
        ScreenSection(
            title = "Trending",
            defaultSelectedItemIndex = selectedDayIndex,
            onSelectedChanged = { name, id -> selectedItemIndexUpdate(name, id) }
        )

        LazyColumn {
            items(
                items = movies,
                key = { it.id }
            ) { movie ->
                Text(
                    text = movie.title,
                    modifier = Modifier.clickable { movieDetailsUpdate(movie.id) }
                )
            }
        }
    }
}
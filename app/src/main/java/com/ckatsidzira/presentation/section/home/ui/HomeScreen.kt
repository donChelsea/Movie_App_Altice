package com.ckatsidzira.presentation.section.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ckatsidzira.presentation.custom.states.ShowError
import com.ckatsidzira.presentation.custom.states.ShowLoading
import com.ckatsidzira.presentation.custom.states.ShowOffline
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.section.home.HomeUiAction
import com.ckatsidzira.presentation.section.home.HomeUiState
import com.ckatsidzira.presentation.section.home.HomeViewModel
import com.ckatsidzira.presentation.section.home.ScreenData

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
            movies = state.screenData.items,
            onAction = onAction,
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    movies: List<MovieUiModel>,
    onAction: (HomeUiAction) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        LazyColumn {
            items(items = movies) { movie ->
                Text(text = movie.title)
            }
        }
    }
}
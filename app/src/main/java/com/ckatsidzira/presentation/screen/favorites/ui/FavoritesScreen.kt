package com.ckatsidzira.presentation.screen.favorites.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ckatsidzira.presentation.custom.card.ListCard
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.navigation.Screen
import com.ckatsidzira.presentation.screen.favorites.FavoritesUiAction
import com.ckatsidzira.presentation.screen.favorites.FavoritesUiEvent
import com.ckatsidzira.presentation.screen.favorites.FavoritesViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.events.collect { event ->
            when (event) {
                is FavoritesUiEvent.OnMovieClicked -> navController.navigate(
                    Screen.Details.withArgs(
                        event.id
                    )
                )
            }
        }
    }

    FavoritesContent(
        modifier = modifier,
        favorites = state.favorites,
        onAction = viewModel::handleAction
    )
}

@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
    favorites: List<MovieUiModel>,
    onAction: (FavoritesUiAction) -> Unit,
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
    ) {
        items(
            items = favorites,
            key = { it.id }
        ) { movie ->
            ListCard(
                modifier = Modifier,
                movie = movie,
                onMovieClicked = { onAction(FavoritesUiAction.OnMovieClicked(it)) },
                onDeleteClicked = { onAction(FavoritesUiAction.OnRemoveFromFavorites(it)) },
            )
        }
    }
}
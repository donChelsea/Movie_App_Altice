package com.ckatsidzira.presentation.screen.favorites.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.screen.favorites.FavoritesViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesContent(favorites = state.favorites)
}

@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
    favorites: List<MovieUiModel>
) {
    LazyColumn(modifier = modifier) {
        items(
            items = favorites,
            key = { it.id }
        ) { movie ->
            Text(text = movie.title)
        }
    }
}
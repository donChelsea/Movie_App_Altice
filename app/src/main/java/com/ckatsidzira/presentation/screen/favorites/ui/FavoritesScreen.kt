package com.ckatsidzira.presentation.screen.favorites.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ckatsidzira.presentation.custom.card.ListCard
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.screen.favorites.FavoritesViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesContent(
        modifier = modifier,
        favorites = state.favorites
    )
}

@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
    favorites: List<MovieUiModel>
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
                onClick = {},
                onDelete = {}
            )
        }
    }
}
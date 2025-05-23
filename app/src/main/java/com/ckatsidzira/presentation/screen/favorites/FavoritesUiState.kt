package com.ckatsidzira.presentation.screen.favorites

import androidx.compose.runtime.Immutable
import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.model.TimeWindow
import com.ckatsidzira.presentation.model.MovieUiModel

@Immutable
data class FavoritesUiState(
    val favorites: List<MovieUiModel> = emptyList(),
)

@Immutable
sealed class FavoritesUiEvent {
    @Immutable
    data class OnMovieClicked(val id: Int): FavoritesUiEvent()
}

@Immutable
sealed class FavoritesUiAction {
    @Immutable
    data class OnMovieClicked(val id: Int): FavoritesUiAction()
    @Immutable
    data class OnRemoveFromFavorites(val movie: MovieUiModel): FavoritesUiAction()
}
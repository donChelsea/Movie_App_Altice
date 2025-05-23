package com.ckatsidzira.presentation.screen.details

import androidx.compose.runtime.Immutable
import com.ckatsidzira.presentation.custom.favorite.FavoriteState
import com.ckatsidzira.presentation.model.MovieUiModel

@Immutable
data class DetailsUiState(
    val screenData: ScreenData = ScreenData.Initial
)

@Immutable
sealed class DetailsUiEvent

@Immutable
sealed class DetailsUiAction {
    data class OnUpdateFavorites(
        val movie: MovieUiModel,
        val isFavorited: Boolean
    ): DetailsUiAction()
}

@Immutable
sealed class ScreenData {
    data object Initial : ScreenData()
    data object Loading : ScreenData()
    data object Offline : ScreenData()

    @Immutable
    data class Error(val message: String) : ScreenData()

    @Immutable
    data class Data(
        val movie: MovieUiModel = MovieUiModel(),
        val favoriteState: FavoriteState = FavoriteState.NotFavorite,
    ) : ScreenData()
}
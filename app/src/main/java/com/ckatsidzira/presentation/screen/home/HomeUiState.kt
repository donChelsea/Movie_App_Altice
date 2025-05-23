package com.ckatsidzira.presentation.screen.home

import androidx.compose.runtime.Immutable
import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.model.TimeWindow
import com.ckatsidzira.presentation.model.MovieUiModel

@Immutable
data class HomeUiState(
    val screenData: ScreenData = ScreenData.Initial
)

@Immutable
sealed class HomeUiEvent {
    @Immutable
    data class OnMovieClicked(val id: Int): HomeUiEvent()
}

@Immutable
sealed class HomeUiAction {
    @Immutable
    data class OnMovieClicked(val id: Int): HomeUiAction()
    @Immutable
    data class OnAddToFavorites(val movie: MovieUiModel): HomeUiAction()
    @Immutable
    data class OnTimeWindowChanged(val timeWindow: String, val timeWindowIndex: Int): HomeUiAction()
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
        val trending: List<MovieUiModel> = emptyList(),
        val favorites: List<MovieUiModel> = emptyList(),
        val selectedTimeWindowIndex: Int = 0,
        val timeWindow: String = TimeWindow.DAY.name.lowercase()
    ) : ScreenData()
}
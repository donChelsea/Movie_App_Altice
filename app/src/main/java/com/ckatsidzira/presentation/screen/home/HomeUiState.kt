package com.ckatsidzira.presentation.screen.home

import androidx.compose.runtime.Immutable
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
        val items: List<MovieUiModel> = emptyList(),
    ) : ScreenData()
}
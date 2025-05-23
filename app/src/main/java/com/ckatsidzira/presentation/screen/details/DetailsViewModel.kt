package com.ckatsidzira.presentation.screen.details

import androidx.lifecycle.SavedStateHandle
import com.ckatsidzira.domain.repository.MovieRepository
import com.ckatsidzira.domain.util.Resource
import com.ckatsidzira.presentation.custom.favorite.FavoriteState
import com.ckatsidzira.presentation.navigation.Screen.DetailArgs.ID
import com.ckatsidzira.presentation.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsUiAction>() {

    private val _state = MutableStateFlow(DetailsUiState())
    override val state: StateFlow<DetailsUiState>
        get() = _state.asStateFlow()

    private val movieId: Int = savedStateHandle[ID] ?: throw Exception("Must have movie id")
    private var favoriteState: FavoriteState = FavoriteState.NotFavorite

    init {
        loadMovie()
    }

    override fun handleAction(action: DetailsUiAction) {
        when (action) {
            is DetailsUiAction.OnUpdateFavorites -> {
                safeLaunch {
                    if (action.isFavorited) {
                        repository.removeFromFavorites(action.movie.toDomain())
                        favoriteState = FavoriteState.NotFavorite
                        updateState(ScreenData.Data(
                            movie = action.movie,
                            favoriteState = favoriteState
                        ))
                    } else {
                        repository.addToFavorites(action.movie.toDomain())
                        favoriteState = FavoriteState.Favorite
                        updateState(ScreenData.Data(
                            movie = action.movie,
                            favoriteState = favoriteState
                        ))
                    }
                }
            }
        }
    }

    private fun loadMovie() {
        safeLaunch {
            val details = async { repository.getMovieDetails(movieId) }.await()
            val favorites = repository.getFavoritesFlow()
                .first { it is Resource.Success }
                .data.orEmpty()

            when (details) {
                is Resource.Error -> updateState(
                    ScreenData.Error(
                        details.message ?: "Error while loading movie."
                    )
                )

                is Resource.Loading -> updateState(ScreenData.Loading)
                is Resource.Success -> details.data?.let { movie ->
                    favorites.forEach { savedMovie ->
                        if (savedMovie.id == movieId) {
                            favoriteState = FavoriteState.Favorite
                        }
                    }
                    updateState(
                        ScreenData.Data(
                            movie = movie.toUiModel(),
                            favoriteState = favoriteState,
                        )
                    )
                }
            }
        }
    }

    private fun updateState(
        screenData: ScreenData = _state.value.screenData
    ) = _state.update { oldState ->
        oldState.copy(screenData = screenData)
    }
}
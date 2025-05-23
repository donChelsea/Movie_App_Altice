package com.ckatsidzira.presentation.screen.favorites

import androidx.lifecycle.viewModelScope
import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.model.TimeWindow
import com.ckatsidzira.domain.repository.MovieRepository
import com.ckatsidzira.domain.util.Resource
import com.ckatsidzira.presentation.model.MovieUiModel
import com.ckatsidzira.presentation.screen.home.ScreenData
import com.ckatsidzira.presentation.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MovieRepository,
) : BaseViewModel<FavoritesUiState, FavoritesUiEvent, FavoritesUiAction>() {

    private val _state = MutableStateFlow(FavoritesUiState())
    override val state: StateFlow<FavoritesUiState>
        get() = _state.asStateFlow()

    init {
        loadFavorites()
    }

    override fun handleAction(action: FavoritesUiAction) {
        when (action) {
            is FavoritesUiAction.OnMovieClicked -> viewModelScope.launch {
                _events.emit(
                    FavoritesUiEvent.OnMovieClicked(id = action.id)
                )
            }

            is FavoritesUiAction.OnRemoveFromFavorites -> {
                println(action.movie)
                toggleFavorite(movie = action.movie.toDomain())
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoritesFlow()
                .collectLatest { resource ->
                    if (resource is Resource.Success) {
                        updateState(favorites = resource.data.orEmpty().map { it.toUiModel() })
                    }
                }
        }
    }

    private fun toggleFavorite(movie: Movie) {
        safeLaunch {
            val isCurrentlyFavorite = repository.isFavorite(movie.id)
            if (isCurrentlyFavorite) {
                repository.removeFromFavorites(movie)
            } else {
                repository.addToFavorites(movie)
            }

            val updatedFavoriteMovies = _state.value.favorites.toMutableList()
            if (isCurrentlyFavorite) {
                updatedFavoriteMovies.removeAll { it.id == movie.id }
            } else {
                updatedFavoriteMovies.add(movie.toUiModel())
            }

            updateState(favorites = updatedFavoriteMovies)
        }
    }

    private fun updateState(
        favorites: List<MovieUiModel> = _state.value.favorites
    ) = _state.update { oldState ->
        oldState.copy(favorites = favorites)
    }
}
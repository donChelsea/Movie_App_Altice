package com.ckatsidzira.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.model.TimeWindow
import com.ckatsidzira.domain.repository.MovieRepository
import com.ckatsidzira.domain.util.Resource
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
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiAction>() {

    private val _state = MutableStateFlow(HomeUiState())
    override val state: StateFlow<HomeUiState>
        get() = _state.asStateFlow()

    init {
        loadTrending()
        loadFavorites()
    }

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnMovieClicked -> viewModelScope.launch {
                _events.emit(
                    HomeUiEvent.OnMovieClicked(id = action.id)
                )
            }

            is HomeUiAction.OnTimeWindowChanged -> loadTrending(
                timeWindow = action.timeWindow,
                selectedTimeWindowIndex = action.timeWindowIndex
            )

            is HomeUiAction.OnAddToFavorites -> {
                println(action.movie)
                toggleFavorite(movie = action.movie.toDomain())
            }
        }
    }

    private fun loadTrending(
        timeWindow: String = TimeWindow.DAY.name.lowercase(),
        selectedTimeWindowIndex: Int = 0,
    ) {
        viewModelScope.launch {
            repository.getTrendingFlow(
                timeWindow = timeWindow
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> updateState(
                        screenData = ScreenData.Error(
                            message = resource.message ?: "Error occurred while getting data."
                        )
                    )

                    is Resource.Loading -> updateState(
                        screenData = ScreenData.Loading
                    )

                    is Resource.Success -> resource.data?.let { movies ->
                        updateState(
                            screenData = ScreenData.Data(
                                selectedTimeWindowIndex = selectedTimeWindowIndex,
                                timeWindow = timeWindow,
                                trending = movies.map { it.toUiModel() }
                            )
                        )
                    }
                }
            }
        }
    }

    private fun updateState(
        screenData: ScreenData = _state.value.screenData
    ) = _state.update { oldState ->
        oldState.copy(screenData = screenData)
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoritesFlow()
                .collectLatest { resource ->
                    if (resource is Resource.Success) {
                        val currentScreenData = _state.value.screenData
                        if (currentScreenData is ScreenData.Data) {
                            println(resource.data.toString())
                            updateState(
                                screenData = currentScreenData.copy(
                                    favorites = resource.data.orEmpty().map { it.toUiModel() }
                                )
                            )
                        }
                    }
                }
        }
    }


    private fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            val currentScreenData = _state.value.screenData
            if (currentScreenData is ScreenData.Data) {
                val isCurrentlyFavorite = repository.isFavorite(movie.id)
                if (isCurrentlyFavorite) {
                    repository.removeFromFavorites(movie)
                } else {
                    repository.addToFavorites(movie)
                }

                val updatedFavoriteMovies = currentScreenData.favorites.toMutableList()
                if (isCurrentlyFavorite) {
                    updatedFavoriteMovies.removeAll { it.id == movie.id }
                } else {
                    updatedFavoriteMovies.add(movie.toUiModel())
                }

                updateState(
                    screenData = currentScreenData.copy(
                        favorites = updatedFavoriteMovies
                    )
                )
            }
        }
    }
}
package com.ckatsidzira.presentation.section.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckatsidzira.domain.model.TimeWindow
import com.ckatsidzira.domain.repository.MovieRepository
import com.ckatsidzira.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState>
        get() = _state.asStateFlow()

    private val _events = MutableSharedFlow<HomeUiEvent>()
    val events: SharedFlow<HomeUiEvent> = _events.asSharedFlow()

    init {
        getData()
    }

    fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnMovieClicked -> viewModelScope.launch {
                _events.emit(
                    HomeUiEvent.OnMovieClicked(id = action.id)
                )
            }
        }
    }

    private fun getData(
        timeWindow: String = TimeWindow.DAY.value
    ) {
        viewModelScope.launch {
            repository.getTrendingFlow(
                timeWindow = timeWindow
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> updateState(
                        screenData = ScreenData.Error(
                            message = result.message ?: "Error occurred while getting data."
                        )
                    )

                    is Resource.Loading -> updateState(
                        screenData = ScreenData.Loading
                    )

                    is Resource.Success -> result.data?.let { movies ->
                        updateState(
                            screenData = ScreenData.Data(
                                items = movies.map { it.toUiModel() }
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
}
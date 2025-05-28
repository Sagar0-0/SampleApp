package com.sagar.sampleapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagar.sampleapp.UiAction
import com.sagar.sampleapp.data.db.MovieEntity
import com.sagar.sampleapp.data.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    private var _isExpanded = MutableStateFlow(false)
    private val _watchFilter = MutableStateFlow(0)
    private val _moviesList = moviesRepo.getAllMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val filteredMovies = combine(_moviesList, _watchFilter) { movies, filter ->
        movies.filter { it.watchStatus == filter }
    }

    val state = combine(filteredMovies, _isExpanded, _watchFilter) { movies, isExpanded, filter ->
        UiState(
            moviesList = movies,
            isExpanded = isExpanded,
            watchFilter = filter
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState()
    )

    fun onUiAction(uiAction: UiAction) {
        when(uiAction) {
            UiAction.IsExpandedChanged -> {
                _isExpanded.value = !_isExpanded.value
            }

            is UiAction.OnFilterChange -> {
                _watchFilter.value = uiAction.watchedStatus
                _isExpanded.value = !_isExpanded.value
            }

            is UiAction.MoveToWatchList -> {
                viewModelScope.launch {
                    moviesRepo.moveToWatchList(uiAction.movie)
                }
            }

            is UiAction.MoveToWatched -> {
                viewModelScope.launch {
                    moviesRepo.moveToWatched(uiAction.movie)
                }
            }
        }
    }
}

data class UiState(
    val isExpanded: Boolean = false,
    val watchFilter: Int = 0,
    val moviesList: List<MovieEntity> = emptyList()
)
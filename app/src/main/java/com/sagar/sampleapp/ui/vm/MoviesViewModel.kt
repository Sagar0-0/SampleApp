package com.sagar.sampleapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagar.sampleapp.UiAction
import com.sagar.sampleapp.data.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    private var _isExpanded = MutableStateFlow(false)
    val isExpanded = _isExpanded.asStateFlow()

    private val _watchFilter = MutableStateFlow(0)
    val watchFilter = _watchFilter.asStateFlow()

    val moviesList = moviesRepo.getAllMovies()
        .stateIn(
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5000),
            scope = viewModelScope
        )

    fun onUiAction(uiAction: UiAction) {
        when(uiAction) {
            UiAction.IsExpandedChanged -> {
                _isExpanded.value = !_isExpanded.value
            }
            is UiAction.OnFilterChange -> {
                _watchFilter.value = uiAction.watchedStatus
            }
        }
    }
}
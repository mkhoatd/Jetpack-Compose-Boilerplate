package com.aregyan.compose.ui.dogs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aregyan.compose.repository.DogsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DogsViewModel(
    private val dogsRepository: DogsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DogUiState())
    val uiState = _uiState.asStateFlow()

    fun search(text: String) {
        _uiState.update {
            it.copy(
                searchText = text
            )
        }
    }

    fun toggleSearch() {
        _uiState.update {
            it.copy(
                searchWidgetState = if (it.searchWidgetState == SearchWidgetState.CLOSED) {
                    SearchWidgetState.OPENED
                } else {
                    SearchWidgetState.CLOSED
                }
            )
        }
    }


    init {
        viewModelScope.launch {
            val dogs = dogsRepository.refreshDogs()
            _uiState.update {
                it.copy(
                    dogList = dogs
                )
            }
        }
    }
}
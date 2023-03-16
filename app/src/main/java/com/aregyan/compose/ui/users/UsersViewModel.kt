package com.aregyan.compose.ui.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aregyan.compose.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {



    var uiState by mutableStateOf(UsersUiState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.refreshUsers()
            usersRepository.users.collect { list ->
                withContext(Dispatchers.Main) {
                    uiState = if (list.isNullOrEmpty()) {
                        uiState.copy(offline = true)
                    } else {
                        uiState.copy(
                            list = list,
                            offline = false
                        )
                    }
                }
            }
        }
    }

}
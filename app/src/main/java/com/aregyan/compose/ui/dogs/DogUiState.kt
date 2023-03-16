package com.aregyan.compose.ui.dogs

import com.aregyan.compose.domain.Dog

data class DogUiState(
    val dogList: List<Dog> = listOf(),
    val searchText: String = "",
    val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,

    ) {
    fun getSearchList(): List<Dog> {
        if (searchText == "") return dogList
        return dogList.filter { it.name.contains(searchText) }
    }
}
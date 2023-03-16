package com.aregyan.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aregyan.compose.ui.dogs.DogScreen
import com.aregyan.compose.ui.dogs.DogsGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogApp() {
   DogScreen()
}

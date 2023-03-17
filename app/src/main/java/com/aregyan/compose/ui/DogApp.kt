package com.aregyan.compose.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.aregyan.compose.ui.dogs.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogApp() {
    DestinationsNavHost(navGraph = NavGraphs.dogsApp)
}

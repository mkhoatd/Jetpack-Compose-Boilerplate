package com.aregyan.compose.ui.dogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@DogsAppNavGraph()
@Destination
@Composable
fun DogDetailScreen(
    destinationsNavigator: DestinationsNavigator, dogId: Int
) {
    val viewModel = getViewModel<DogsViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    val dog = uiState.value.dogList.single { it.id == dogId }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row {
                    IconButton(onClick = { destinationsNavigator.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(text = dog.name)
                }
            })
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxHeight(0.3f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                AsyncImage(model = dog.url, contentDescription = "Dog image")
            }
            Column(modifier = Modifier.padding(it)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Breed for:",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth(0.3f)
                    )
                    Text(text = dog.bredFor)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Breed group:",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth(0.3f)
                    )
                    Text(text = dog.breedGroup)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Life span:",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth(0.3f)
                    )
                    Text(text = dog.lifeSpan)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Origin:",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth(0.3f)
                    )
                    Text(text = dog.origin)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Temperament:",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth(0.3f)
                    )
                    Text(text = dog.temperament)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Height:",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth(0.3f)
                    )
                    Text(text = dog.height)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Weight:",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth(0.3f)
                    )
                    Text(text = dog.weight)
                }

            }
        }


    }
}

@file:OptIn(ExperimentalMaterial3Api::class)

package com.aregyan.compose.ui.dogs

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aregyan.compose.domain.Dog
import com.aregyan.compose.ui.dogs.destinations.DogDetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@DogsAppNavGraph(start = true)
@Destination
@Composable
fun DogScreen(
    destinationsNavigator: DestinationsNavigator
) {
    val viewModel = getViewModel<DogsViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    val searchedList by remember {
        derivedStateOf {
            if (uiState.value.searchText == "") uiState.value.dogList
            uiState.value.dogList.filter { it.name.contains(uiState.value.searchText) }
        }
    }
    Scaffold(
        topBar = {
            DogAppBar(viewModel, uiState.value)
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Box(modifier = Modifier.padding(it)) {
            DogsGrid(
                destinationsNavigator, searchedList
            )
        }
    }
}

@Composable
fun DogsGrid(
    destinationsNavigator: DestinationsNavigator,
    dogsDisplayList: List<Dog>
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp)
    ) {
        items(dogsDisplayList) {
            DogItem(destinationsNavigator, item = it)
        }
    }

}

@Composable
fun DogAppBar(
    viewModel: DogsViewModel, uiState: DogUiState
) {
    when (uiState.searchWidgetState) {
        SearchWidgetState.CLOSED -> DefaultAppBar(viewModel)
        SearchWidgetState.OPENED -> SearchAppBar(viewModel, uiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    viewModel: DogsViewModel
) {
    TopAppBar(
        modifier = Modifier.height(56.dp),
        title = {
            Text(
                text = "Dog app v1", color = MaterialTheme.colorScheme.onSurface
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        actions = {
            IconButton(
                onClick = { viewModel.toggleSearch() },
                content = {
                    Icon(
                        Icons.Filled.Search, contentDescription = "Search"
                    );
                },
            );
        },
    );
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    viewModel: DogsViewModel, uiState: DogUiState
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp), color = MaterialTheme.colorScheme.surface
    ) {
        TextField(
            value = uiState.searchText, onValueChange = viewModel::search,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.onSurface,
                textColor = MaterialTheme.colorScheme.onSurface,
                placeholderColor = MaterialTheme.colorScheme.onSurface,
            ),
            leadingIcon = {
                IconButton(onClick = { viewModel.search(uiState.searchText) }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search item",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.search(""); viewModel.toggleSearch() }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.search(uiState.searchText)
                    viewModel.toggleSearch()
                },
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
        )
    }
}


@Composable
fun DogItem(destinationsNavigator: DestinationsNavigator, item: Dog) {
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .height(250.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        var click by remember { mutableStateOf(false) }
        when (click) {
            false -> DogCardFront(destinationsNavigator,
                item,
                onClick = { click = true; Log.d("click", "$click") })
            true -> DogCardBack(item, onClick = { click = false })
        }
    }

}

@Composable
fun DogCardBack(item: Dog, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "Name",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = item.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "Origin",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = item.origin,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displaySmall,
                )
            }
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun DogCardFront(
    destinationsNavigator: DestinationsNavigator, item: Dog, onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {onClick()},
                    onDoubleTap = {
                        val navArgs = DogDetailScreenDestination.NavArgs(item.id)
                        destinationsNavigator.navigate(DogDetailScreenDestination(navArgs))}
                )
            },
    ) {
        AsyncImage(
            modifier = Modifier.size(100.dp),
            model = item.url,
            contentDescription = null,
        )
        Row(
            modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = item.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = item.origin,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Icon(
                Icons.Outlined.FavoriteBorder, contentDescription = "Like"
            )
        }
    }
}
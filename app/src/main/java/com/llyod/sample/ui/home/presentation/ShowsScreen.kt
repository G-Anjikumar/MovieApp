package com.llyod.sample.ui.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.llyod.sample.ui.home.presentation.components.ShowItem
import com.llyod.sample.ui.home.viewmodel.ShowListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsScreen(
    showListState: ShowListState,
    navController: NavHostController,
    onEvent: (ShowListUiState) -> Unit,
    searchEvent: (String) -> Unit
) {
    if (showListState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           /* var searchQuery by rememberSaveable { mutableStateOf("") }

            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(20.dp)),
                query = searchQuery,
                onQueryChange = { queryChanged ->
                    searchQuery = queryChanged // update the query state
                    searchEvent(queryChanged) // call the callback
                },
                onSearch = { query ->
                },
                active = true,
                onActiveChange = { isActive ->
                },
                placeholder = { Text("Enter Show Name") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                //trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) }
            ) {
                // Show suggestions here
                // for example a LazyColumn with suggestion items
            }*/
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
            ) {
                items(showListState.showsList.size) { index ->
                    ShowItem(
                        shows = showListState.showsList[index],
                        navHostController = navController
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }
    }
}
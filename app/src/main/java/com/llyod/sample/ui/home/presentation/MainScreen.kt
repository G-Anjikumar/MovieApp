package com.llyod.sample.ui.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.llyod.sample.R
import com.llyod.sample.ui.home.viewmodel.ShowAndCastViewModel
import com.llyod.sample.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()
    val showAndCastViewModel: ShowAndCastViewModel = hiltViewModel()
    val showState = showAndCastViewModel.showListState.collectAsState().value

    Scaffold(bottomBar = {
        BottomNavigationBar(
            bottomNavController = bottomNavController,
            onEvent = showAndCastViewModel::onEvent
        )
    }, topBar = {
        TopAppBar(
            title = {
                Text(
                    text = if (showState.isCurrentShow)
                        stringResource(R.string.shows)
                    else
                        stringResource(R.string.actors),
                    fontSize = TextUnit.Unspecified,
                    color = Color.White
                )
            }, modifier = Modifier.shadow(2.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                Color.Black
            )
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(navController = bottomNavController, startDestination = Screen.Home.route)
            {

                composable(Screen.Home.route) {
                    ShowsScreen(
                        navController = navController,
                        showListState = showState,
                    )
                }
                composable(Screen.PopularShows.route) {
                    CastScreen(
                        showListState = showState,
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    onEvent: (ShowListUiState) -> Unit
) {
    val items = listOf(
        BottomItem(
            title = stringResource(R.string.shows),
            icon = Icons.Rounded.Favorite
        ),
        BottomItem(
            title = stringResource(R.string.actors),
            icon = Icons.Rounded.PlayArrow
        )
    )
    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        Row(modifier = Modifier.background(Color.Black)) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {
                                onEvent(ShowListUiState.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.Home.route){
                                    launchSingleTop = true
                                }
                            }

                            1 -> {
                                onEvent(ShowListUiState.Paginate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.PopularShows.route){
                                    launchSingleTop =true
                                }
                            }

                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = if (selected.intValue == index) Color.Red else Color.White
                        )
                    },
                    label = {
                        Text(text = bottomItem.title, color = Color.White)
                    }
                )
            }
        }
    }
}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)
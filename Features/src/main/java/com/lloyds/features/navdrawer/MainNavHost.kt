package com.lloyds.features.navdrawer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lloyds.features.shows.DetailsScreen
import com.lloyds.features.splash.SplashScreen
import com.lloyds.features.utils.Screen


@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen {
                navController.navigate(it)
            }
        }
        composable(Screen.Shows.route) {
            MainScreen(navController)
        }
        composable(Screen.Details.route + "/{showId}",
            arguments = listOf(
                navArgument("showId") { type = NavType.IntType }
            )
        ) {
            DetailsScreen()
        }
    }
}
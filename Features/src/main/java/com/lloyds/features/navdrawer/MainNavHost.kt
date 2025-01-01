package com.lloyds.features.navdrawer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lloyds.features.shows.DetailsScreen
import com.lloyds.features.splash.SplashScreen
import com.lloyds.features.utils.Screen


@Composable
fun MainNavHost(
    navController: NavHostController,
    route: String
) {
    NavHost(navController = navController, startDestination = route) {
        composable(route) {
            SplashScreen {
                navController.navigate(it)
            }
        }
        composable(route) {
            MainScreen(navController)
        }
        composable(
            "$route/{showId}",
            arguments = listOf(
                navArgument("showId") { type = NavType.IntType }
            )
        ) {
            DetailsScreen()
        }
    }
}
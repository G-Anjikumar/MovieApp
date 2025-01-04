package com.lloyds.features.utils

sealed class Screen(val route:String) {

    data object Shows:Screen("Main")
    data object PopularShows:Screen("popularMovie")
    data object Details:Screen("Details")
    data object Splash:Screen("Splash")
}
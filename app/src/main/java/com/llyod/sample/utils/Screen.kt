package com.llyod.sample.utils

sealed class Screen(val route:String) {

    data object Home:Screen("Main")
    data object PopularShows:Screen("popularMovie")
    data object Details:Screen("Details")
    data object Splash:Screen("Splash")
}
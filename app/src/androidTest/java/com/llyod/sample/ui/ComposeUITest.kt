package com.llyod.sample.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lloyds.features.shows.ShowItem
import com.llyod.remote.data.model.Image
import com.llyod.remote.data.model.Rating
import com.llyod.remote.data.model.Shows
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComposeUITest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun runShowScreenValidData() {
        val mockShow = Shows(
            id = 1,
            name = "Test Show",
            image = Image(
                medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                original = ""
            ),
            rating = Rating(average = 4.5),
            premiered = "2013-06-24"
        )
        lateinit var navController: NavHostController
        composeRule.setContent {
            navController = rememberNavController()
            ShowItem(shows = mockShow, navHostController = navController)
        }

        composeRule.onNodeWithText(mockShow.name!!).assertIsDisplayed()

        composeRule.onNodeWithText(mockShow.name!!).assertIsDisplayed()

        composeRule.onNodeWithText(mockShow.premiered!!).assertIsDisplayed()
    }

    @Test
    fun runShowScreenInvalidImageUrl() {
        val mockShow = Shows(
            id = 1,
            name = "Test Show",
            image = Image(medium = "Invalid Url", original = ""),
            rating = Rating(average = 4.5),
            premiered = "2013-06-24"
        )

        composeRule.setContent {
            val navController = rememberNavController()
            ShowItem(shows = mockShow, navHostController = navController)
        }

        composeRule
            .onNodeWithContentDescription("Test Show")
            .assertIsDisplayed()
    }
}

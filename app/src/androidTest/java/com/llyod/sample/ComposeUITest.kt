package com.llyod.sample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.llyod.sample.data.remote.model.Image
import com.llyod.sample.data.remote.model.Rating
import com.llyod.sample.data.remote.model.Shows
import com.llyod.sample.ui.home.presentation.components.ShowItem
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
            rating = Rating(average = 4.5)
        )
        lateinit var navController: NavHostController
        composeRule.setContent {
            navController = rememberNavController()
            ShowItem(shows = mockShow, navHostController = navController)
        }

        composeRule.onNodeWithText("Test Show").assertIsDisplayed()

        composeRule.onNodeWithText("Test Show").assertIsDisplayed()

        composeRule.onNodeWithText("4.5").assertIsDisplayed()
    }

    @Test
    fun runShowScreenInvalidImageUrl() {
        val mockShow = Shows(
            id = 1,
            name = "Test Show",
            image = Image(medium = "Invalid Url", original = ""),
            rating = Rating(average = 4.5)
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

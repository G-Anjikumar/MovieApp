package com.llyod.sample.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.lloyds.features.cast.components.HorizontalCastItem
import org.junit.Rule
import org.junit.Test

class HorizontalItemUITestCase {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun horizontalCastItem_displaysCorrectData() {
        val name = "Name"
        val countryName = "country"
        val imageUrl = "https://example.com/image.jpg"
        val birthDay = "2013-06-24"

        composeTestRule.setContent {
            HorizontalCastItem(
                name = name,
                countryName = countryName,
                imageUrl = imageUrl,
                birthDay = birthDay,
            )
        }

        composeTestRule.onNodeWithText(name).assertIsDisplayed()
        composeTestRule.onNodeWithText(birthDay).assertIsDisplayed()
    }

    @Test
    fun horizontalCastItem_showsErrorIcon_whenImageFailsToLoad() {
        val name = "Name"
        val countryName = "Country"
        val imageUrl = "invalid_url"
        val birthDay = "1990-01-01"

        composeTestRule.setContent {
            HorizontalCastItem(
                name = name,
                countryName = countryName,
                imageUrl = imageUrl,
                birthDay = birthDay,
            )
        }

        composeTestRule
            .onNodeWithContentDescription(countryName)
            .assertIsDisplayed()
    }
}
package com.llyod.sample

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import retrofit2.http.GET


class LazyVerticalGridTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val sampleShowsList = listOf(
        Show(id = 1, title = "Show 1"),
        Show(id = 2, title = "Show 2"),
        Show(id = 3, title = "Show 3"),
        Show(id = 4, title = "Show 4")
    )

    @Test
    fun lazyVerticalGridDisplaysAllItems(){
        composeRule.setContent {

        }
    }
}

data class Show(val id:Int,val title:String)
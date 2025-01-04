package com.llyod.sample.cast

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.lloyds.features.cast.CastViewModel
import com.llyod.remote.data.model.Cast
import com.llyod.remote.data.model.Character
import com.llyod.remote.data.model.Country
import com.llyod.remote.data.model.ImageX
import com.llyod.remote.data.model.Links
import com.llyod.remote.data.model.LinksX
import com.llyod.remote.data.model.Person
import com.llyod.remote.data.model.Previousepisode
import com.llyod.remote.data.model.Self
import com.llyod.remote.data.model.SelfX
import com.llyod.remote.domain.repository.CastRepository
import com.llyod.remote.utils.Response
import com.llyod.remote.utils.UiState
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.whenever
import javax.inject.Inject

@HiltAndroidTest
class CastViewModelTest {
    val castListMock = listOf(
        Cast(
            character = Character(
                linksX = LinksX(self = SelfX("https://example.com/image.jpg")),
                id = 1,
                image = ImageX("https://example.com/image.jpg", "https://example.com/image.jpg"),
                name = "Test",
                "https://example.com/image.jpg"
            ), person = Person(
                links = Links(
                    previousepisode = Previousepisode("Test1", "Name"),
                    self = Self("Test1")
                ),
                "23-07-1997",
                country = Country("11", "Name", "GMT"),
                "NA",
                "Male",
                1,
                image = ImageX("https://example.com/image.jpg", "https://example.com/image.jpg"),
                "Name",
                1,
                "https://example.com/image.jpg"
            ),
            self = true,
            voice = false
        ),
        Cast(
            character = Character(
                linksX = LinksX(self = SelfX("https://example.com/image.jpg")),
                id = 2,
                image = ImageX("https://example.com/image.jpg", "https://example.com/image.jpg"),
                name = "Test",
                "https://example.com/image.jpg"
            ), person = Person(
                links = Links(
                    previousepisode = Previousepisode("Test2", "Name"),
                    self = Self("Test2")
                ),
                "03-12-1999",
                country = Country("22", "Name", "GMT"),
                "NA",
                "Male",
                2,
                image = ImageX("https://example.com/image.jpg", "https://example.com/image.jpg"),
                "Name",
                2,
                "https://example.com/image.jpg"
            ),
            self = true,
            voice = false
        ),
    )
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: CastRepository

    private lateinit var viewModel: CastViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = CastViewModel(repository)
    }

    @Test
    fun testCase(): Unit = runTest {
        whenever(repository.getCastList()).thenReturn(flow {
            emit(Response.Loading())
            delay(100)
            emit(Response.Success(castListMock))
        })
        val states = mutableListOf<UiState>()
        val job = launch {
            viewModel.castListState.collect { states.add(it) }
        }
        advanceUntilIdle()
        assertTrue(states[0].isLoading) // Loading state
        assertEquals(states[1].caseList,states[1].caseList)// Success state
        assertFalse(states[1].isLoading)

        job.cancel()
    }
}
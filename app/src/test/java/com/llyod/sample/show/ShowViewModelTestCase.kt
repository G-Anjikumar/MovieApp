package com.llyod.sample.show

import app.cash.turbine.test
import com.lloyds.features.cast.CastViewModel
import com.lloyds.features.shows.ShowsViewModel
import com.llyod.remote.data.model.Image
import com.llyod.remote.data.model.Rating
import com.llyod.remote.data.model.Schedule
import com.llyod.remote.data.model.Shows
import com.llyod.remote.domain.repository.ShowRepository
import com.llyod.remote.utils.Response
import com.llyod.remote.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class ShowViewModelTestCase {

    @Mock
    private lateinit var showRepository: ShowRepository
    private lateinit var viewModel: ShowsViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val mockedShowsList = listOf(
        Shows(
            id = 1,
            name = "Show 1",
            genres = listOf("Drama", "Comedy"),
            language = "English",
            rating = Rating(average = 7.8),
            image = Image("https://example.com/image.jpg", "https://example.com/image.jpg"),
            schedule = Schedule(listOf("day1", "day2"), "9:40")
        ),
        Shows(
            id = 2,
            name = "Show 2",
            genres = listOf("Action", "Adventure"),
            language = "English",
            rating = Rating(average = 8.2),
            image = Image("https://example.com/image.jpg", "https://example.com/image.jpg"),
            schedule = Schedule(listOf("day1", "day2"), "9:40")
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ShowsViewModel(showRepository)
        whenever(showRepository.getShowList()).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Success(mockedShowsList))
        })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getShowList_emits_loading_and_success_states(): Unit = runTest {
        viewModel.showListState.test {
            Assert.assertEquals(UiState(), awaitItem())
            advanceUntilIdle()
            viewModel.getShowList()
            Assert.assertEquals(UiState(isLoading = true), awaitItem())
            Assert.assertEquals(
                UiState(
                    isLoading = false,
                    showsList = mockedShowsList,
                    isCurrentShow = false
                ),
                awaitItem()
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getShowList_emits_loading_and_empty_states() = runTest {
        whenever(showRepository.getShowList()).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Success(emptyList()))
        })
        viewModel = ShowsViewModel(showRepository)

        viewModel.showListState.test {
            Assert.assertEquals(UiState(), awaitItem())
            advanceUntilIdle()
            viewModel.getShowList()
            Assert.assertEquals(UiState(isLoading = true), awaitItem())
            Assert.assertEquals(
                UiState(isLoading = false, caseList = emptyList(), isCurrentShow = false),
                awaitItem()
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCastList_emits_loading_and_error_states() = runTest {
        val errorMessage = "Failed to fetch cast list"
        whenever(showRepository.getShowList()).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Error(errorMessage))
        })
        viewModel = ShowsViewModel(showRepository)

        viewModel.showListState.test {
            Assert.assertEquals(UiState(), awaitItem())
            advanceUntilIdle()
            viewModel.getShowList()
            Assert.assertEquals(UiState(isLoading = true), awaitItem())
            Assert.assertEquals(
                UiState(isLoading = false, error = errorMessage),
                awaitItem()
            )
        }
    }
}
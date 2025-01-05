package com.llyod.sample.show

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.lloyds.features.shows.DetailViewModel
import com.lloyds.features.shows.DetailsState
import com.llyod.remote.data.model.Image
import com.llyod.remote.data.model.Rating
import com.llyod.remote.data.model.Schedule
import com.llyod.remote.data.model.Shows
import com.llyod.remote.domain.repository.ShowRepository
import com.llyod.remote.utils.AppConstants
import com.llyod.remote.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class ShowDetailsTestCase {

    @Mock
    private lateinit var showRepository: ShowRepository
    private lateinit var viewModel: DetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val show = Shows(
        id = 1,
        name = "Show 1",
        genres = listOf("Drama", "Comedy"),
        language = "English",
        rating = Rating(average = 7.8),
        image = Image("https://example.com/image.jpg", "https://example.com/image.jpg"),
        schedule = Schedule(listOf("day1", "day2"), "9:40")
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        val savedStateHandle = SavedStateHandle(mapOf("showId" to 1))
        viewModel = DetailViewModel(showRepository, savedStateHandle)
        whenever(showRepository.getShow(1)).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Success(show))
        })
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getShowDetails_emits_success_state() = runTest {
        whenever(showRepository.getShow(1)).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Success(show))
        })
        viewModel.detailsState.test {
            assertEquals(DetailsState(), awaitItem())
            assertEquals(DetailsState(isLoading = true), awaitItem())
            assertEquals(DetailsState(isLoading = false, shows = show), awaitItem())
        }
    }

    @Test
    fun getShowList_emits_loading_and_empty_states() = runTest {
        whenever(showRepository.getShow(-1)).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Error(AppConstants.API_ERROR_MESSAGE))
        })
        val savedStateHandle = SavedStateHandle(mapOf("showId" to -1))
        viewModel = DetailViewModel(showRepository, savedStateHandle)
        viewModel.detailsState.test {
            assertEquals(DetailsState(), awaitItem())
            assertEquals(DetailsState(isLoading = true), awaitItem())
            assertEquals(
                DetailsState(isLoading = false, error = AppConstants.API_ERROR_MESSAGE),
                awaitItem()
            )
        }
    }
}
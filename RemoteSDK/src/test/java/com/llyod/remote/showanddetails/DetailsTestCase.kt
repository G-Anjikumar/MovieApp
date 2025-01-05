package com.llyod.remote.showanddetails

import com.llyod.remote.data.model.Image
import com.llyod.remote.data.model.Rating
import com.llyod.remote.data.model.Schedule
import com.llyod.remote.data.model.Shows
import com.llyod.remote.data.remote.FetchData
import com.llyod.remote.data.repository.ShowRepositoryImpl
import com.llyod.remote.domain.repository.ShowRepository
import com.llyod.remote.utils.AppConstants
import com.llyod.remote.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetailsTestCase {

    private val show = Shows(
        id = 1,
        name = "Show 1",
        genres = listOf("Drama", "Comedy"),
        language = "English",
        rating = Rating(average = 7.8),
        image = Image("https://example.com/image.jpg", "https://example.com/image.jpg"),
        schedule = Schedule(listOf("day1", "day2"), "9:40")
    )

    private lateinit var showRepository: ShowRepository
    private lateinit var fetchData: FetchData

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fetchData = mock()
        showRepository = ShowRepositoryImpl(fetchData)
    }

    @Test
    fun `test getShowDetails with success response`(): Unit = runTest {
        whenever(fetchData.getShow(1)).thenReturn(show)
        val result = showRepository.getShow(1).toList()
        assert(result[0] is Response.Loading)
        assert(result[1] is Response.Success)
        assertEquals(show, (result[1] as Response.Success).data)
    }

    @Test
    fun `test getShow with invalid ID response`(): Unit = runTest {
        whenever(fetchData.getShow(-1)).thenThrow(RuntimeException(AppConstants.API_ERROR_MESSAGE))
        val result = showRepository.getShow(-1).toList()
        assert(result[0] is Response.Loading)
        assert(result[1] is Response.Error)
        assertEquals(AppConstants.API_ERROR_MESSAGE, (result[1] as Response.Error).message)
    }
}
package com.llyod.sample

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
import com.llyod.remote.utils.AppConstants
import com.llyod.remote.utils.Response
import com.llyod.remote.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
//@HiltAndroidTest
class CastViewModelTestCase {

    @Mock
    lateinit var castRepository: CastRepository
    lateinit var castViewModel: CastViewModel
    val testDispatcher = StandardTestDispatcher()
/*    @Mock
    lateinit var fetchData: FetchData*/
/*    @get:Rule
    var hiltRule = HiltAndroidRule(this)*/

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
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

    @Before
    fun setUp() {
//        hiltRule.inject()
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
//        castRepository=CastRepositoryImplemented(fetchData)
        castViewModel = CastViewModel(castRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getCastList with success response`(): Unit = runTest {
//        val mockResponse = flowOf(Response.Success(castListMock))
        val mockResponse = flow {
            emit(Response.Loading())
            emit(Response.Success(castListMock))
        }
        whenever(castRepository.getCastList()).thenReturn(mockResponse)
        val uiStates = mutableListOf<UiState>()
        val job = launch {
            castViewModel.castListState.collect { state ->
                uiStates.add(state)
            }
        }
//        advanceUntilIdle()
        castViewModel.getCastList()
        withTimeout(1000) {
            while (uiStates.size < 2) {
                delay(50) // Wait for states to be emitted
            }
        }

//        assert(uiStates.first().isLoading)
        assert(uiStates.size >= 2)
        assert(!uiStates[0].isLoading)
        val finalState = uiStates.last()
        assert(!finalState.isLoading)
        assert(finalState.caseList == castListMock)
        assert(finalState.error == null)
        job.cancel()

/*        val uiStates = mutableListOf<UiState>()

        // Stub the repository method to return the mockResponse
        val mockResponse = flowOf(Response.Success(castListMock))
        val apiResponse = castRepository.getCastList()
        whenever(apiResponse).thenReturn(mockResponse)

        // Collect UI state updates
        val job = launch {
            castViewModel.castListState.collect {
                uiStates.add(it)
            }
        }

        // Call the method under test
        castViewModel.getCastList()

        // Validate UI states
        val uiState = castViewModel.castListState.first()
        assert(!uiState.isLoading)
        assert(uiState.caseList == castListMock)

        // Cancel the job
        job.cancel()

        // Verify that the repository method was called
        verify(castRepository).getCastList()*/
    }

    @Test
    fun `test getCastList with error response`(): Unit = runTest {
        whenever(castRepository.getCastList()).thenReturn(flowOf(Response.Error(AppConstants.NETWORK_ERROR_MESSAGE)))
        val uiStates = mutableListOf<UiState>()
        val job = launch {
            castViewModel.castListState.collect {
                uiStates.add(it)
            }
        }
        castViewModel.getCastList()
        assert(uiStates[0].isLoading)
        assert(!uiStates[1].isLoading)
        assertEquals(AppConstants.NETWORK_ERROR_MESSAGE, uiStates[1].error)
        job.cancel()
    }
}

@ExperimentalCoroutinesApi
class MainDispatcherRule : TestWatcher() {
    private val testDispatcher = StandardTestDispatcher()

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
package com.llyod.sample.cast

import app.cash.turbine.test
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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


class CastViewModelTestCases {

    @Mock
    private lateinit var castRepository: CastRepository
    private lateinit var viewModel: CastViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val castListMock = listOf(
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CastViewModel(castRepository)
        whenever(castRepository.getCastList()).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Success(castListMock))
        })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCastList_emits_loading_and_success_states(): Unit = runTest {
        viewModel.castListState.test {
            assertEquals(UiState(), awaitItem())
            advanceUntilIdle()
            viewModel.getCastList()
            assertEquals(UiState(isLoading = true), awaitItem())
            assertEquals(
                UiState(
                    isLoading = false,
                    caseList = castListMock,
                    isCurrentShow = false
                ),
                awaitItem()
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCastList_emits_loading_and_empty_states() = runTest {
        whenever(castRepository.getCastList()).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Success(emptyList()))
        })
        viewModel = CastViewModel(castRepository)

        viewModel.castListState.test {
            assertEquals(UiState(), awaitItem())
            advanceUntilIdle()
            viewModel.getCastList()
            assertEquals(UiState(isLoading = true), awaitItem())
            assertEquals(
                UiState(isLoading = false, caseList = emptyList(), isCurrentShow = false),
                awaitItem()
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCastList_emits_loading_and_error_states() = runTest {
        whenever(castRepository.getCastList()).thenReturn(flow {
            emit(Response.Loading())
            emit(Response.Error(AppConstants.API_ERROR_MESSAGE))
        })
        viewModel = CastViewModel(castRepository)

        viewModel.castListState.test {
            assertEquals(UiState(), awaitItem())
            advanceUntilIdle()
            viewModel.getCastList()
            assertEquals(UiState(isLoading = true), awaitItem())
            assertEquals(
                UiState(isLoading = false, error = AppConstants.API_ERROR_MESSAGE),
                awaitItem()
            )
        }
    }
}
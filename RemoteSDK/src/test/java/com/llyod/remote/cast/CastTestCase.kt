package com.llyod.remote.cast

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
import com.llyod.remote.data.remote.FetchData
import com.llyod.remote.data.repository.CastRepositoryImplemented
import com.llyod.remote.domain.repository.CastRepository
import com.llyod.remote.utils.AppConstants
import com.llyod.remote.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CastTestCase {

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
    private lateinit var castRepository: CastRepository
    private lateinit var fetchData: FetchData

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fetchData = mock()
        castRepository = CastRepositoryImplemented(fetchData)
    }

    @Test
    fun `test getCastList with success response`(): Unit = runTest {
        whenever(fetchData.getCastList()).thenReturn(castListMock)
        val result = castRepository.getCastList().toList()
        assert(result[0] is Response.Loading)
        assert(result[1] is Response.Success)
        Assert.assertEquals(castListMock, (result[1] as Response.Success).data)
    }

    @Test
    fun `test getCastList with empty response`(): Unit = runTest {
        whenever(fetchData.getCastList()).thenReturn(emptyList())
        val result = castRepository.getCastList().toList()
        assert(result[0] is Response.Loading)
        assert(result[1] is Response.Error)
        Assert.assertEquals(AppConstants.API_ERROR_MESSAGE, (result[1] as Response.Error).message)
    }

    @Test
    fun `test getCastList with failure response`(): Unit = runTest {
        whenever(fetchData.getCastList()).thenThrow(RuntimeException(AppConstants.NETWORK_ERROR_MESSAGE))
        val result = castRepository.getCastList().toList()
        assert(result[0] is Response.Loading)
        assert(result[1] is Response.Error)
        Assert.assertEquals(
            AppConstants.NETWORK_ERROR_MESSAGE,
            (result[1] as Response.Error).message
        )
    }
}
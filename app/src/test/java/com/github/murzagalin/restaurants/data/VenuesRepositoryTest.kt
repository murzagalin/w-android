package com.github.murzagalin.restaurants.data

import app.cash.turbine.test
import com.github.murzagalin.restaurants.domain.LocationCoordinates
import com.github.murzagalin.restaurants.domain.VenuesData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito.*
import org.mockito.invocation.InvocationOnMock
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertIs

class VenuesRepositoryTest {

    private lateinit var api: VenuesApi
    private lateinit var favoritesStorage: FavoritesStorage
    private lateinit var venuesMapper: VenuesApiMapper
    private lateinit var subject: VenuesRepository

    private val TEST_NAME = "Test Name"
    private val TEST_PAGE_TITLE = "Test Page Title"
    private val TEST_URL = "http://example.com/image.jpg"
    private val TEST_VENUE_ID = "1"
    private val TEST_VENUE_NAME = "Venue 1"
    private val TEST_VENUE_DESCRIPTION = "Description 1"

    private val testParams = LocationCoordinates(
        longitude = 10.0,
        latitude = 20.0
    )
    private val testResponseData = ResponseData(
        name = TEST_NAME,
        pageTitle = TEST_PAGE_TITLE,
        sections = listOf(
            SectionApiModel(
                items = listOf(
                    ItemApiModel(
                        image = ImageApiModel(url = TEST_URL),
                        venue = VenueApiModel(
                            id = TEST_VENUE_ID,
                            name = TEST_VENUE_NAME,
                            shortDescription = TEST_VENUE_DESCRIPTION
                        )
                    )
                )
            )
        )
    )

    @Before
    fun setUp() {
        api = mock(VenuesApi::class.java)
        favoritesStorage = mock(FavoritesStorage::class.java)
        venuesMapper = mock(VenuesApiMapper::class.java)
        subject = VenuesRepository(api, favoritesStorage, venuesMapper)
    }

    @Test
    fun `getVenues should return mapped VenuesData`() = runTest {
        val favouritesStates = mapOf(TEST_VENUE_ID to true)
        val expectedVenuesData = VenuesData(
            name = TEST_NAME,
            pageTitle = TEST_PAGE_TITLE,
            venues = listOf(
                com.github.murzagalin.restaurants.domain.Venue(
                    id = TEST_VENUE_ID,
                    name = TEST_VENUE_NAME,
                    description = TEST_VENUE_DESCRIPTION,
                    imageUrl = TEST_URL,
                    isFavourite = true
                )
            )
        )

        whenever(api.getVenues(anyOrNull(), anyOrNull())).thenReturn(testResponseData)
        whenever(favoritesStorage.favoritesFlow).thenReturn(flowOf(favouritesStates))
        whenever(venuesMapper.map(anyOrNull(), anyOrNull())).thenReturn(expectedVenuesData)

        subject.getVenues(testParams).test {
            assertEquals(expectedVenuesData, awaitItem())
            awaitComplete()
        }

        verify(api).getVenues(
            latitude = testParams.latitude,
            longitude = testParams.longitude
        )
        verify(favoritesStorage).favoritesFlow
        verify(venuesMapper).map(testResponseData, favouritesStates)
    }


    @Test
    fun `mapException should map HttpException with HTTP_GATEWAY_TIMEOUT to NetworkException`() = runTest {
        val httpException = HttpException(retrofit2.Response.error<Any>(HttpURLConnection.HTTP_GATEWAY_TIMEOUT, okhttp3.ResponseBody.create(null, "")))
        whenever(api.getVenues(anyOrNull(), anyOrNull())).thenThrow(httpException)

        subject.getVenues(testParams).test {
            assertIs<NetworkException>(awaitError())
        }
    }

    @Test
    fun `mapException should map IOException to NetworkException`() = runTest {
        doAnswer { invocation: InvocationOnMock? ->
            throw IOException("IO error")
        }.whenever(api).getVenues(anyOrNull(), anyOrNull())

        subject.getVenues(testParams).test {
            assertIs<NetworkException>(awaitError())
        }
    }

    @Test
    fun `mapException should map HttpException with other codes to UndefinedException`() = runTest {
        val httpException = HttpException(retrofit2.Response.error<Any>(HttpURLConnection.HTTP_BAD_REQUEST, okhttp3.ResponseBody.create(null, "")))
        whenever(api.getVenues(anyOrNull(), anyOrNull())).thenThrow(httpException)

        subject.getVenues(testParams).test {
            assertIs<UndefinedException>(awaitError())
        }
    }

    @Test
    fun `mapException should map other exceptions to UndefinedException`() = runTest {
        val exception = RuntimeException("Generic error")
        doThrow(exception).whenever(api).getVenues(anyOrNull(), anyOrNull())

        subject.getVenues(testParams).test {
            assertIs<UndefinedException>(awaitError())
        }
    }
}
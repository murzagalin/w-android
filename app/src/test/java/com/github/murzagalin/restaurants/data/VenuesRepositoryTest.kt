package com.github.murzagalin.restaurants.data

import com.github.murzagalin.restaurants.domain.GetVenuesParams
import com.github.murzagalin.restaurants.domain.VenuesData
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

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

    private val testParams = GetVenuesParams(
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
        whenever(favoritesStorage.isFavorite(anyOrNull())).thenReturn(true)
        whenever(venuesMapper.map(anyOrNull(), anyOrNull())).thenReturn(expectedVenuesData)

        // When
        val result = subject.getVenues(testParams)

        // Then
        assertEquals(expectedVenuesData, result)
        verify(api).getVenues(
            latitude = testParams.latitude,
            longitude = testParams.longitude
        )
        verify(favoritesStorage).isFavorite(TEST_VENUE_ID)
        verify(venuesMapper).map(testResponseData, favouritesStates)
    }
}
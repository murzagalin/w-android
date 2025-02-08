package com.github.murzagalin.restaurants.data

import com.github.murzagalin.restaurants.domain.Venue
import com.github.murzagalin.restaurants.domain.VenuesData
import kotlin.test.Test
import kotlin.test.assertEquals

class VenuesApiMapperTest {

    private val TEST_NAME = "Test Name"
    private val TEST_PAGE_TITLE = "Test Page Title"
    private val TEST_URL = "http://example.com/image.jpg"
    private val TEST_VENUE_ID = "1"
    private val TEST_VENUE_NAME = "Venue 1"
    private val TEST_VENUE_DESCRIPTION = "Description 1"

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

    private val favouritesStates = mapOf(TEST_VENUE_ID to true)

    private val expectedVenuesData = VenuesData(
        name = TEST_NAME,
        pageTitle = TEST_PAGE_TITLE,
        venues = listOf(
            Venue(
                id = TEST_VENUE_ID,
                name = TEST_VENUE_NAME,
                description = TEST_VENUE_DESCRIPTION,
                imageUrl = TEST_URL,
                isFavourite = true
            )
        )
    )

    private val subject = VenuesApiMapper()

    @Test
    fun `map should return mapped VenuesData`() {

        // When
        val result = subject.map(testResponseData, favouritesStates)

        // Then
        assertEquals(expectedVenuesData, result)
    }
}
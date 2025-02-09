package com.github.murzagalin.restaurants.ui

import com.github.murzagalin.restaurants.domain.Venue
import com.github.murzagalin.restaurants.domain.VenuesData
import org.junit.Assert.assertEquals
import org.junit.Test

class VenuesUiMapperTest {

    private val mapper = VenuesUiMapper()

    @Test
    fun `mapToUiModel should map VenuesData to VenuesDataUiModel`() {
        val venuesData = VenuesData(
            name = "Test Name",
            pageTitle = "Test Page Title",
            venues = listOf(
                Venue("1", "Venue 1", "Description 1", "ImageUrl 1", true),
                Venue("2", "Venue 2", "Description 2", "ImageUrl 2", false)
            )
        )

        val expectedUiModel = VenuesDataUiModel(
            pageTitle = "Test Page Title",
            venues = listOf(
                VenueUiModel("1", "Venue 1", "Description 1", "ImageUrl 1", true),
                VenueUiModel("2", "Venue 2", "Description 2", "ImageUrl 2", false)
            )
        )

        val actualUiModel = mapper.mapToUiModel(venuesData)

        assertEquals(expectedUiModel, actualUiModel)
    }

    @Test
    fun `mapToUiModel should limit the number of venues to MAX_VENUES_COUNT`() {
        val venues = List(20) { index ->
            Venue(index.toString(), "Venue $index", "Description $index", "ImageUrl $index", index % 2 == 0)
        }
        val venuesData = VenuesData(
            name = "Test Name",
            pageTitle = "Test Page Title",
            venues = venues
        )

        val actualUiModel = mapper.mapToUiModel(venuesData)

        assertEquals(VenuesUiMapper.MAX_VENUES_COUNT, actualUiModel.venues.size)
    }
}
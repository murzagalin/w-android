package com.github.murzagalin.restaurants.ui
import com.github.murzagalin.restaurants.domain.VenuesData
import com.github.murzagalin.restaurants.domain.Venue

class VenuesUiMapper {

    companion object {
        const val MAX_VENUES_COUNT = 15
    }

    fun mapToUiModel(venuesData: VenuesData): VenuesDataUiModel {
        return VenuesDataUiModel(
            pageTitle = venuesData.pageTitle,
            venues = venuesData.venues.take(MAX_VENUES_COUNT).map { mapToUiModel(it) }
        )
    }

    private fun mapToUiModel(venue: Venue): VenueUiModel {
        return VenueUiModel(
            id = venue.id,
            name = venue.name,
            description = venue.description,
            imageUrl = venue.imageUrl,
            isFavourite = venue.isFavourite
        )
    }
}
package com.github.murzagalin.restaurants.ui
import com.github.murzagalin.restaurants.domain.Venue
import com.github.murzagalin.restaurants.domain.VenuesData
import javax.inject.Inject

class VenuesUiMapper @Inject constructor() {

    companion object {
        const val MAX_VENUES_COUNT = 15
    }

    fun map(venuesData: VenuesData): VenuesDataUiModel {
        return VenuesDataUiModel(
            pageTitle = venuesData.pageTitle,
            venues = venuesData.venues.take(MAX_VENUES_COUNT).map { map(it) }
        )
    }

    private fun map(venue: Venue): VenueUiModel {
        return VenueUiModel(
            id = venue.id,
            name = venue.name,
            description = venue.description,
            imageUrl = venue.imageUrl,
            isFavourite = venue.isFavourite
        )
    }
}
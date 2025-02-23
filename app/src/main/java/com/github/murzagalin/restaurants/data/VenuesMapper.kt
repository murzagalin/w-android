package com.github.murzagalin.restaurants.data

import com.github.murzagalin.restaurants.domain.Venue
import com.github.murzagalin.restaurants.domain.VenuesData
import javax.inject.Inject

class VenuesApiMapper @Inject constructor() {

    fun map(
        responseData: ResponseData,
        favouritesStates: Map<String, Boolean>
    ) = VenuesData(
        name = responseData.name,
        pageTitle = responseData.pageTitle,
        venues = responseData.sections
            .flatMap { it.items }
            .mapNotNull { item ->
                val venue = item.venue ?: return@mapNotNull null
                Venue(
                    id = venue.id,
                    name = venue.name,
                    description = venue.shortDescription.orEmpty(),
                    imageUrl = item.image.url,
                    isFavourite = favouritesStates.getOrDefault(venue.id, false)
                )
            }
    )

}

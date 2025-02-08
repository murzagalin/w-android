package com.github.murzagalin.restaurants.data

import com.github.murzagalin.restaurants.domain.GetVenuesParams
import com.github.murzagalin.restaurants.domain.IVenuesRepository
import com.github.murzagalin.restaurants.domain.VenuesData

class VenuesRepository(
    private val api: VenuesApi,
    private val favoritesStorage: FavoritesStorage,
    private val venuesMapper: VenuesApiMapper
) : IVenuesRepository {

    override suspend fun getVenues(params: GetVenuesParams): VenuesData {
        val response = api.getVenues(
            longitude = params.longitude,
            latitude = params.latitude
        )

        val favouritesStates = response.sections
            .flatMap { section -> section.items }
            .mapNotNull { item -> item.venue }
            .associate { venue -> venue.id to favoritesStorage.isFavorite(venue.id) }

        return venuesMapper.map(response, favouritesStates)
    }
}

package com.github.murzagalin.restaurants.domain

import kotlinx.coroutines.flow.Flow

interface IVenuesRepository {
    suspend fun getVenues(params: LocationCoordinates): Flow<VenuesData>
}
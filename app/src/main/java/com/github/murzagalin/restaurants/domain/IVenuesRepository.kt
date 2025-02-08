package com.github.murzagalin.restaurants.domain

interface IVenuesRepository {
    suspend fun getVenues(params: LocationCoordinates): VenuesData
}
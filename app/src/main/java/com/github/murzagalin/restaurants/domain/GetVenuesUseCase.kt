package com.github.murzagalin.restaurants.domain

class GetVenuesUseCase(private val venuesRepository: IVenuesRepository) {

    suspend operator fun invoke(params: LocationCoordinates): VenuesData {
        return venuesRepository.getVenues(params)
    }
}
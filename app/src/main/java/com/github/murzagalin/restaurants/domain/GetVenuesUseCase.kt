package com.github.murzagalin.restaurants.domain

import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val venuesRepository: IVenuesRepository
) {

    suspend operator fun invoke(params: LocationCoordinates): VenuesData {
        return venuesRepository.getVenues(params)
    }
}
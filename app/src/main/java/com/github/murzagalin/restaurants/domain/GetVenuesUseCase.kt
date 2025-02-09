package com.github.murzagalin.restaurants.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val venuesRepository: IVenuesRepository
) {

    suspend operator fun invoke(params: LocationCoordinates): Flow<VenuesData> {
        return venuesRepository.getVenues(params)
    }
}
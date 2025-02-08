package com.github.murzagalin.restaurants.domain

class GetVenuesUseCase(private val venuesRepository: IVenuesRepository) {

    suspend fun invoke(params: GetVenuesParams): VenuesData {
        return venuesRepository.getVenues(params)
    }
}
package com.github.murzagalin.restaurants.domain

import javax.inject.Inject

class SetFavoriteUseCase @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) {

    operator fun invoke(venueId: String, isFavorite: Boolean) {
        favoritesRepository.setFavorite(venueId, isFavorite)
    }
}
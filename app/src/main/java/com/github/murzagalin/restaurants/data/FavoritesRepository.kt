package com.github.murzagalin.restaurants.data

import com.github.murzagalin.restaurants.domain.IFavoritesRepository
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val favoritesStorage: FavoritesStorage
) : IFavoritesRepository {

    override fun setFavorite(venueId: String, isFavorite: Boolean) {
        favoritesStorage.setFavorite(venueId, isFavorite)
    }
}
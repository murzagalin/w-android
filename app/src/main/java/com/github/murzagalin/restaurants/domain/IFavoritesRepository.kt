package com.github.murzagalin.restaurants.domain

interface IFavoritesRepository {
    fun setFavorite(venueId: String, isFavorite: Boolean)
}
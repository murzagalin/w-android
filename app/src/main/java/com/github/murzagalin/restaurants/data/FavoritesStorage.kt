package com.github.murzagalin.restaurants.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class FavoritesStorage @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

    fun setFavorite(venueId: String, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(venueId, isFavorite).apply()
    }

    fun isFavorite(venueId: String): Boolean {
        return sharedPreferences.getBoolean(venueId, false)
    }
}
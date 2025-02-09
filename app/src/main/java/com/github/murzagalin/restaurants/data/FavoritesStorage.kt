package com.github.murzagalin.restaurants.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class FavoritesStorage @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
    private val _favoritesFlow by lazy { MutableStateFlow(loadFavorites()) }
    val favoritesFlow: Flow<Map<String, Boolean>>
        get() = _favoritesFlow

    fun setFavorite(venueId: String, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(venueId, isFavorite).apply()
        _favoritesFlow.value = loadFavorites()
    }

    private fun loadFavorites(): Map<String, Boolean> {
        return sharedPreferences.all.filterValues { it is Boolean }.mapValues { it.value as Boolean }
    }
}
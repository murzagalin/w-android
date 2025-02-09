package com.github.murzagalin.restaurants.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class FavoritesRepositoryTest {

    private lateinit var favoritesStorage: FavoritesStorage

    private lateinit var favoritesRepository: FavoritesRepository

    @Before
    fun setUp() {
        favoritesStorage = mock()
        favoritesRepository = FavoritesRepository(favoritesStorage)
    }

    @Test
    fun `setFavorite should call setFavorite on favoritesStorage`() = runTest {
        val venueId = "testVenueId"
        val isFavorite = true

        favoritesRepository.setFavorite(venueId, isFavorite)

        verify(favoritesStorage).setFavorite(venueId, isFavorite)
    }
}
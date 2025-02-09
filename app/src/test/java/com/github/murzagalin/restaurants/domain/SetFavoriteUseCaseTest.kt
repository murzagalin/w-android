package com.github.murzagalin.restaurants.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class SetFavoriteUseCaseTest {

    private lateinit var favoritesRepository: IFavoritesRepository

    private lateinit var setFavoriteUseCase: SetFavoriteUseCase

    @Before
    fun setUp() {
        favoritesRepository = mock()
        setFavoriteUseCase = SetFavoriteUseCase(favoritesRepository)
    }

    @Test
    fun `invoke should call setFavorite on favoritesRepository`() = runTest {
        val venueId = "testVenueId"
        val isFavorite = true

        setFavoriteUseCase(venueId, isFavorite)

        verify(favoritesRepository).setFavorite(venueId, isFavorite)
    }
}
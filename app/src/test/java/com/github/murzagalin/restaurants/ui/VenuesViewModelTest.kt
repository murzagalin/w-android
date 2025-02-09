package com.github.murzagalin.restaurants.ui

import app.cash.turbine.test
import com.github.murzagalin.restaurants.MainDispatcherRule
import com.github.murzagalin.restaurants.domain.GetLocationsUseCase
import com.github.murzagalin.restaurants.domain.GetVenuesUseCase
import com.github.murzagalin.restaurants.domain.LocationCoordinates
import com.github.murzagalin.restaurants.domain.SetFavoriteUseCase
import com.github.murzagalin.restaurants.domain.VenuesData
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

class VenuesViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val subscribeToLocations: GetLocationsUseCase = mock()
    private val getVenues: GetVenuesUseCase = mock()
    private val setFavorite: SetFavoriteUseCase = mock()
    private val uiMapper: VenuesUiMapper = mock()

    private val viewModel: VenuesViewModel by lazy {
        VenuesViewModel(
            subscribeToLocations = subscribeToLocations,
            getVenues = getVenues,
            setFavorite = setFavorite,
            uiMapper = uiMapper
        )
    }

    private val location = LocationCoordinates(0.0, 10.0)
    private val TEST_NAME = "TestName"
    private val venuesData = VenuesData(TEST_NAME, "TEST PAGE TITLE", emptyList())
    private val venuesUIData = VenuesDataUiModel(TEST_NAME, emptyList())


    @Test
    fun `test venues flow emits loading, then content`() = runTest {
        whenever(subscribeToLocations()).thenReturn(flowOf(location))
        whenever(getVenues(any())).thenReturn(flowOf(venuesData))
        whenever(uiMapper.map(any())).thenReturn(venuesUIData)

        viewModel.venuesFlow.test {
            val contentState = awaitItem()
            assertIs<VenuesViewModel.ViewState.Content>(contentState)
            assertEquals(venuesUIData, contentState.venuesData)
            assertFalse(contentState.isLoading)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test venues flow emits error state when getVenues throws error`() = runTest {
        val error = RuntimeException("test")
        whenever(subscribeToLocations()).thenReturn(flowOf(location))
        whenever(getVenues(location)).thenThrow(error)

        viewModel.venuesFlow.test {
            val errorState = awaitItem()
            assertIs<VenuesViewModel.ViewState.Error>(errorState)
            assertEquals(error.message, errorState.error.message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test venues flow emits error state when subscribeToLocations throws error`() = runTest {
        val error = RuntimeException("test")
        whenever(subscribeToLocations()).thenAnswer {
            flow<LocationCoordinates> { throw error }
        }

        viewModel.venuesFlow.test {
            val errorState = awaitItem()
            assertIs<VenuesViewModel.ViewState.Error>(errorState)
            assertEquals(error.message, errorState.error.message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test toggleFavorite calls setFavorite with correct parameters`() = runTest {
        val venueId = "venue-id"
        val isFavorite = true

        viewModel.toggleFavorite(venueId, isFavorite)

        verify(setFavorite).invoke(venueId, isFavorite)
    }
}
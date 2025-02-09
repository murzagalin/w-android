package com.github.murzagalin.restaurants.ui

import app.cash.turbine.test
import com.github.murzagalin.restaurants.MainDispatcherRule
import com.github.murzagalin.restaurants.domain.GetLocationsUseCase
import com.github.murzagalin.restaurants.domain.GetVenuesUseCase
import com.github.murzagalin.restaurants.domain.LocationCoordinates
import com.github.murzagalin.restaurants.domain.VenuesData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertIs

class VenuesViewModelTest {
/*
    private val viewModel: VenuesViewModel by lazy {
        VenuesViewModel(locationsUseCase, venuesUseCase)
    }
    private val locationsUseCase: GetLocationsUseCase = mock()
    private val venuesUseCase: GetVenuesUseCase = mock()

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `test success state`() = runTest {
        val locationCoordinates = LocationCoordinates(0.0, 1.0)
        whenever(locationsUseCase()).thenReturn(flowOf(locationCoordinates))
        whenever(venuesUseCase(any())).thenReturn(VenuesData("TEST NAME", "TEST TITLE", emptyList()))

        viewModel.venuesFlow.test {
            assertIs<VenuesViewModel.ViewState.Success>(awaitItem())
        }
    }

    @Test
    fun `test error state`() = runTest {
        val exception = RuntimeException("Test exception")
        val locationCoordinates = LocationCoordinates(0.0, 1.0)
        whenever(locationsUseCase()).thenReturn(flowOf(locationCoordinates))
        whenever(venuesUseCase(any())).thenThrow(exception)

        viewModel.venuesFlow.test {
            val state = awaitItem()
            assertIs<VenuesViewModel.ViewState.Error>(state)
            assertIs<RuntimeException>(state.error)
            assertEquals(exception.message, state.error.message)
        }
    }*/
}
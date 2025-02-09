package com.github.murzagalin.restaurants.domain

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetLocationsUseCaseTest {

    private lateinit var getLocationsUseCase: GetLocationsUseCase

    @Before
    fun setUp() {
        getLocationsUseCase = GetLocationsUseCase()
    }

    @Test
    fun `invoke should emit locations in sequence`() = runTest {

        val expectedLocations = GetLocationsUseCase.locations

        getLocationsUseCase().test {
            expectedLocations.forEach { expectedLocation ->
                assertEquals(expectedLocation, awaitItem())
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke should emit locations with delay`() = runTest {
        val expectedDelay = 10_000L
        val expectedFirstLocation = GetLocationsUseCase.locations[0]
        val expectedSecondLocation = GetLocationsUseCase.locations[1]

        getLocationsUseCase().test {
            assertEquals(expectedFirstLocation, awaitItem())
            advanceTimeBy(expectedDelay)
            assertEquals(expectedSecondLocation, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
package com.github.murzagalin.restaurants.domain

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class GetVenuesUseCaseTest {

    @Mock
    private lateinit var venuesRepository: IVenuesRepository

    private lateinit var subject: GetVenuesUseCase

    private val TEST_URL = "http://example.com/image.jpg"

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        subject = GetVenuesUseCase(venuesRepository)
    }

    @Test
    fun `invoke should return venues data`() = runTest {
        // Given
        val params = GetVenuesParams(10.0, 20.0)
        val expectedVenuesData = VenuesData(
            name = "Test Name",
            pageTitle = "Test Page Title",
            venues = listOf(
                Venue("1", "Venue 1", "Description 1", TEST_URL, true),
                Venue("2", "Venue 2", "Description 2", TEST_URL, false)
            )
        )
        whenever(venuesRepository.getVenues(params)).thenReturn(expectedVenuesData)

        // When
        val result = subject.invoke(params)

        // Then
        assertEquals(result, expectedVenuesData)
        verify(venuesRepository).getVenues(params)
    }

    @Test(expected = Exception::class)
    fun `invoke should throw exception when repository fails`() = runTest {
        // Given
        val params = GetVenuesParams(10.0, 20.0)
        whenever(venuesRepository.getVenues(params)).thenThrow(Exception::class.java)

        // When
        subject.invoke(params)

        // Then
        // Exception is expected
    }
}
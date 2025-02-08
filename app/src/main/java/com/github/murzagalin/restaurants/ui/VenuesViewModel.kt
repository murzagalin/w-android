package com.github.murzagalin.restaurants.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.murzagalin.restaurants.AppDispatchers
import com.github.murzagalin.restaurants.domain.GetLocationsUseCase
import com.github.murzagalin.restaurants.domain.GetVenuesUseCase
import com.github.murzagalin.restaurants.domain.VenuesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val subscribeToLocations: GetLocationsUseCase,
    private val getVenues: GetVenuesUseCase
): ViewModel() {

    val venuesFlow: StateFlow<ViewState>
        get() = _venuesFlow
    private val _venuesFlow = MutableStateFlow<ViewState>(ViewState.Empty)

    init {
        subscribeToVenues()
    }

    private fun subscribeToVenues() {
        viewModelScope.launch {
            subscribeToLocations()
                .onEach { location ->
                    _venuesFlow.value = ViewState.Loading
                }
                .map { getVenues(it) }
                .flowOn(AppDispatchers.io)
                .catch { _venuesFlow.value = ViewState.Error(it) }
                .collect { venues ->
                    _venuesFlow.value = ViewState.Success(venues)
                }
        }
    }

    sealed interface ViewState {

        data object Empty : ViewState

        data object Loading : ViewState

        class Success(val venuesData: VenuesData) : ViewState

        class Error(val error: Throwable) : ViewState

    }
}
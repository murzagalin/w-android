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
    private val locationsUseCase: GetLocationsUseCase,
    private val venuesUseCase: GetVenuesUseCase
): ViewModel() {

    companion object {
        private val TAG = this::class.simpleName
    }

    val venuesFlow: StateFlow<ViewState>
        get() = _venuesFlow
    private val _venuesFlow = MutableStateFlow<ViewState>(ViewState.Empty)

    init {
        subscribeToVenues()
    }

    private fun subscribeToVenues() {
        viewModelScope.launch {
            locationsUseCase()
                .onEach { location ->
                    Log.d(TAG, "Received New Location: $location")
                    _venuesFlow.value = ViewState.Loading
                }
                .map { location ->
                    venuesUseCase(location)
                }
                .flowOn(AppDispatchers.io)
                .catch {
                    Log.d(TAG, "Error in location-venues flow: $it")
                    _venuesFlow.value = ViewState.Error(it)
                }
                .collect { venues ->
                    Log.d(TAG, "New venues: ${venues.name}: ${venues.venues.size}")
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
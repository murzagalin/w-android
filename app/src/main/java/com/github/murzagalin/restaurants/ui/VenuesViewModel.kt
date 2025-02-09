package com.github.murzagalin.restaurants.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.murzagalin.restaurants.AppDispatchers
import com.github.murzagalin.restaurants.domain.GetLocationsUseCase
import com.github.murzagalin.restaurants.domain.GetVenuesUseCase
import com.github.murzagalin.restaurants.domain.SetFavoriteUseCase
import com.github.murzagalin.restaurants.domain.VenuesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class VenuesViewModel @Inject constructor(
    private val subscribeToLocations: GetLocationsUseCase,
    private val getVenues: GetVenuesUseCase,
    private val setFavorite: SetFavoriteUseCase
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
                .flatMapLatest {
                    getVenues(it)
                        .catch {
                            _venuesFlow.value = ViewState.Error(it)
                        }
                }
                .catch {
                    _venuesFlow.value = ViewState.Error(it)
                }
                .flowOn(AppDispatchers.io)
                .collect { venues ->
                    _venuesFlow.value = ViewState.Success(venues)
                }
        }
    }

    fun toggleFavorite(venueId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            setFavorite(venueId, isFavourite)
        }
    }

    sealed interface ViewState {

        data object Empty : ViewState

        data object Loading : ViewState

        class Success(val venuesData: VenuesData) : ViewState

        class Error(val error: Throwable) : ViewState

    }
}
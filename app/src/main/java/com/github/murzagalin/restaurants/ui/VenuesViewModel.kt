package com.github.murzagalin.restaurants.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.murzagalin.restaurants.AppDispatchers
import com.github.murzagalin.restaurants.domain.GetLocationsUseCase
import com.github.murzagalin.restaurants.domain.GetVenuesUseCase
import com.github.murzagalin.restaurants.domain.SetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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
    private val setFavorite: SetFavoriteUseCase,
    private val uiMapper: VenuesUiMapper
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
                .onEach { setLoadingState() }
                .flatMapLatest { location ->
                    getVenues(location)
                        .map { uiMapper.map(it) }
                        .catch { _venuesFlow.value = ViewState.Error(it) }
                }
                .catch {
                    _venuesFlow.value = ViewState.Error(it)
                }
                .flowOn(AppDispatchers.io)
                .collect { venues ->
                    _venuesFlow.value = ViewState.Content(venuesData = venues, isLoading = false)
                }
        }
    }

    private fun setLoadingState() {
        val currentState = _venuesFlow.value
        if (currentState is ViewState.Content) {
            _venuesFlow.value = currentState.copy(isLoading = true)
        } else {
            _venuesFlow.value = ViewState.Content(isLoading = true)
        }
    }


    fun toggleFavorite(venueId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            setFavorite(venueId, isFavourite)
        }
    }

    sealed interface ViewState {

        data object Empty : ViewState

        data class Content(
            val venuesData: VenuesDataUiModel? = null,
            val isLoading: Boolean = false
        ) : ViewState

        class Error(val error: Throwable) : ViewState

    }
}
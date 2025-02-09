package com.github.murzagalin.restaurants.ui

data class VenuesDataUiModel(
    val pageTitle: String,
    val venues: List<VenueUiModel>
)

data class VenueUiModel(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isFavourite: Boolean
)


package com.github.murzagalin.restaurants.domain

data class VenuesData(
    val name: String,
    val pageTitle: String,
    val venues: List<Venue>
)

data class Venue(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isFavourite: Boolean
)



package com.github.murzagalin.restaurants.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseData(
    @Json(name = "name") val name: String,
    @Json(name = "page_title") val pageTitle: String,
    @Json(name = "sections") val sections: List<Section>
)

@JsonClass(generateAdapter = true)
data class Section(
    @Json(name = "items") val items: List<Item>
)

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "image") val image: Image,
    @Json(name = "venue") val venue: Venue?
)

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class Venue(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "short_description") val shortDescription: String,
)

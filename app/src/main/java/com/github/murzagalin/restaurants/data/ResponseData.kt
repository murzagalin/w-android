package com.github.murzagalin.restaurants.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseData(
    @Json(name = "name") val name: String,
    @Json(name = "page_title") val pageTitle: String,
    @Json(name = "sections") val sections: List<SectionApiModel>
)

@JsonClass(generateAdapter = true)
data class SectionApiModel(
    @Json(name = "items") val items: List<ItemApiModel>
)

@JsonClass(generateAdapter = true)
data class ItemApiModel(
    @Json(name = "image") val image: ImageApiModel,
    @Json(name = "venue") val venue: VenueApiModel?
)

@JsonClass(generateAdapter = true)
data class ImageApiModel(
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class VenueApiModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "short_description") val shortDescription: String,
)

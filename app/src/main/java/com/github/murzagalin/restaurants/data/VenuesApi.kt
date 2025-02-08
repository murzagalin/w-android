package com.github.murzagalin.restaurants.data

import retrofit2.http.GET
import retrofit2.http.Path


interface VenuesApi {

    @GET("v1/pages/restaurants?lat={lat}&lon={long}")
    suspend fun getVenues(
        @Path("lat") latitude: Double,
        @Path("long") longitude: Double
    ): ResponseData
}
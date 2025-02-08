package com.github.murzagalin.restaurants.data

import retrofit2.http.GET
import retrofit2.http.Query


interface VenuesApi {

    @GET("v1/pages/restaurants")
    suspend fun getVenues(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): ResponseData
}
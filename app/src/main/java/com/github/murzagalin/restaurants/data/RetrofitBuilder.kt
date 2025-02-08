package com.github.murzagalin.restaurants.data
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {

  fun <T> build(apiInterface: Class<T>): T =
    Retrofit.Builder()
      .addConverterFactory(MoshiConverterFactory.create())
      .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(Level.BASIC)).build())
      .baseUrl("https://restaurant-api.wolt.com/")
      .build()
      .create(apiInterface)
}
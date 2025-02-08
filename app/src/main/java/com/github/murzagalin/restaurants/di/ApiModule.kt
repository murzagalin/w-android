package com.github.murzagalin.restaurants.di

import com.github.murzagalin.restaurants.data.VenuesApi
import com.github.murzagalin.restaurants.data.VenuesRepository
import com.github.murzagalin.restaurants.domain.IVenuesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ApiModule {

    @Binds
    abstract fun bindsRepository(impl: VenuesRepository): IVenuesRepository

    companion object {
        @Provides
        fun provideVenuesApi(): VenuesApi {
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(Level.BASIC)).build())
                .baseUrl("https://restaurant-api.wolt.com/")
                .build()
                .create(VenuesApi::class.java)
        }
    }
}
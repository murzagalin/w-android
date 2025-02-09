package com.github.murzagalin.restaurants.data

import com.github.murzagalin.restaurants.domain.IVenuesRepository
import com.github.murzagalin.restaurants.domain.LocationCoordinates
import com.github.murzagalin.restaurants.domain.VenuesData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class VenuesRepository @Inject constructor(
    private val api: VenuesApi,
    private val favoritesStorage: FavoritesStorage,
    private val venuesMapper: VenuesApiMapper
) : IVenuesRepository {

    override suspend fun getVenues(params: LocationCoordinates): Flow<VenuesData> {
        val response = try {
            api.getVenues(
                longitude = params.longitude,
                latitude = params.latitude
            )
        } catch (e: Exception) {
            throw e.mapException()
        }

        return favoritesStorage.favoritesFlow.map { favourites ->
            venuesMapper.map(response, favourites)
        }
    }


    private fun Exception.mapException(): Exception {
        val newMessage = this.message ?: this.stackTraceToString()

        return when (this) {
            is HttpException -> {
                when (code()) {
                    HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
                        NetworkException(newMessage)
                    }
                    else -> {
                        UndefinedException(newMessage)
                    }
                }
            }
            is IOException -> {
                NetworkException(newMessage)
            }
            else -> {
                UndefinedException(newMessage)
            }
        }
    }
}

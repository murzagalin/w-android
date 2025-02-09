package com.github.murzagalin.restaurants.data

import com.github.murzagalin.restaurants.domain.IVenuesRepository
import com.github.murzagalin.restaurants.domain.LocationCoordinates
import com.github.murzagalin.restaurants.domain.VenuesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class VenuesRepository @Inject constructor(
    private val api: VenuesApi,
    private val favoritesStorage: FavoritesStorage,
    private val venuesMapper: VenuesApiMapper
) : IVenuesRepository {

    override suspend fun getVenues(params: LocationCoordinates): Flow<VenuesData> {
        val responseFlow = flow {
            val response = try {
                api.getVenues(
                    longitude = params.longitude,
                    latitude = params.latitude
                )
            } catch (e: Exception) {
                throw e.mapException()
            }

            emit(response)
        }

        return combine(responseFlow, favoritesStorage.favoritesFlow) { response, favourites ->
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

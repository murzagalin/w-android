package com.github.murzagalin.restaurants.data

import com.github.murzagalin.restaurants.domain.IVenuesRepository
import com.github.murzagalin.restaurants.domain.LocationCoordinates
import com.github.murzagalin.restaurants.domain.VenuesData
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class VenuesRepository @Inject constructor(
    private val api: VenuesApi,
    private val favoritesStorage: FavoritesStorage,
    private val venuesMapper: VenuesApiMapper
) : IVenuesRepository {

    override suspend fun getVenues(params: LocationCoordinates): VenuesData {
        val response = try {
            api.getVenues(
                longitude = params.longitude,
                latitude = params.latitude
            )
        } catch (e: Exception) {
            throw e.mapException()
        }

        val favouritesStates = response.sections
            .flatMap { section -> section.items }
            .mapNotNull { item -> item.venue }
            .associate { venue -> venue.id to favoritesStorage.isFavorite(venue.id) }

        return venuesMapper.map(response, favouritesStates)
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

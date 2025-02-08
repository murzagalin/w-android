package com.github.murzagalin.restaurants.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetLocationsUseCase @Inject constructor() {

    operator fun invoke(): Flow<LocationCoordinates> = flow {
        var index = 0
        while (true) {
            emit(locations[index])
            delay(DELAY)
            index = (index + 1) % locations.size
        }
    }

    companion object {
        private const val DELAY = 10_000L

        val locations = listOf(
            LocationCoordinates(60.169418, 24.931618),
            LocationCoordinates(60.169818, 24.932906),
            LocationCoordinates(60.170005, 24.935105),
            LocationCoordinates(60.169108, 24.936210),
            LocationCoordinates(60.168355, 24.934869),
            LocationCoordinates(60.167560, 24.932562),
            LocationCoordinates(60.168254, 24.931532),
            LocationCoordinates(60.169012, 24.930341),
            LocationCoordinates(60.170085, 24.929569)
        )
    }
}
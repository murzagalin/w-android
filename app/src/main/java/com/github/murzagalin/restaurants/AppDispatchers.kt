package com.github.murzagalin.restaurants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppDispatchers {

    private var _io = Dispatchers.IO

    val io: CoroutineDispatcher
        get() = _io

    fun overrideIoDispatcher(dispatcher: CoroutineDispatcher) {
        _io = dispatcher
    }
}
package dev.maxsiomin.prodhse.core.location

import android.location.Location

interface LocationClient {

    @Throws(LocationException::class)
    suspend fun getLocation(): Location

    class LocationException(override val message: String) : Exception()

}
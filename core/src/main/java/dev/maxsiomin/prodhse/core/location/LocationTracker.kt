package dev.maxsiomin.prodhse.core.location

import android.location.Location

interface LocationTracker {

    suspend fun getCurrentLocation(): Location?

}
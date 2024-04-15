package dev.maxsiomin.prodhse.core.location

import android.location.Location
import dev.maxsiomin.common.domain.LocationError
import dev.maxsiomin.common.domain.Resource

interface LocationTracker {

    suspend fun getCurrentLocation(): Resource<Location, LocationError>

}
package dev.maxsiomin.prodhse.core.location

import android.location.Location
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource

interface LocationTracker {

    suspend fun getCurrentLocation(): Resource<Location, LocationError>

}
package dev.maxsiomin.prodhse.feature.home.domain.repository

import android.location.Location
import dev.maxsiomin.common.domain.LocationError
import dev.maxsiomin.common.domain.Resource

interface LocationRepository {

    suspend fun getCurrentLocation(): Resource<Location, LocationError>

}
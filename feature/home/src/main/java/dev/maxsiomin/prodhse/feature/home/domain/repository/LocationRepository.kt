package dev.maxsiomin.prodhse.feature.home.domain.repository

import android.location.Location
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource

internal interface LocationRepository {

    suspend fun getCurrentLocation(): Resource<Location, LocationError>

}
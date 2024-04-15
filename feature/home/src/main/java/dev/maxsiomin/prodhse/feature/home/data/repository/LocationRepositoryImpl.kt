package dev.maxsiomin.prodhse.feature.home.data.repository

import android.location.Location
import dev.maxsiomin.common.domain.LocationError
import dev.maxsiomin.common.domain.Resource
import dev.maxsiomin.prodhse.core.location.LocationTracker
import dev.maxsiomin.prodhse.feature.home.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationTracker: LocationTracker,
): LocationRepository {

    override suspend fun getCurrentLocation(): Resource<Location, LocationError> {
        return locationTracker.getCurrentLocation()
    }

}
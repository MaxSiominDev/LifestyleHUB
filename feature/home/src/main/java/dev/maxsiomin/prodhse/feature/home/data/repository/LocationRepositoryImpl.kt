package dev.maxsiomin.prodhse.feature.home.data.repository

import android.location.Location
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.location.LocationTracker
import dev.maxsiomin.prodhse.feature.home.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LocationRepositoryImpl @Inject constructor(
    private val locationTracker: LocationTracker,
) : LocationRepository {

    override suspend fun getCurrentLocation(): Resource<Location, LocationError> =
        withContext(Dispatchers.IO) {
            return@withContext locationTracker.getCurrentLocation()
        }

}
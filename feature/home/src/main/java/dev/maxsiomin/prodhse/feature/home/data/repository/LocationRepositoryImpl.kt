package dev.maxsiomin.prodhse.feature.home.data.repository

import android.location.Location
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.location.LocationTracker
import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.feature.home.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LocationRepositoryImpl @Inject constructor(
    private val locationTracker: LocationTracker,
    private val dispatchers: DispatcherProvider,
) : LocationRepository {

    override suspend fun getCurrentLocation(): Resource<Location, LocationError> =
        withContext(dispatchers.io) {
            return@withContext locationTracker.getCurrentLocation()
        }

}
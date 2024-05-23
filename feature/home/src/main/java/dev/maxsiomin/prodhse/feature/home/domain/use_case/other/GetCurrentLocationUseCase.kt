package dev.maxsiomin.prodhse.feature.home.domain.use_case.other

import android.location.Location
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.feature.home.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepo: LocationRepository,
    private val dispatchers: DispatcherProvider,
) {

    suspend operator fun invoke(): Resource<Location, LocationError> = withContext(dispatchers.io) {
        return@withContext locationRepo.getCurrentLocation()
    }

}
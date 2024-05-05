package dev.maxsiomin.prodhse.feature.home.domain.use_case.other

import android.location.Location
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.repository.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(private val locationRepo: LocationRepository) {

    suspend operator fun invoke(): Resource<Location, LocationError> {
        return locationRepo.getCurrentLocation()
    }

}
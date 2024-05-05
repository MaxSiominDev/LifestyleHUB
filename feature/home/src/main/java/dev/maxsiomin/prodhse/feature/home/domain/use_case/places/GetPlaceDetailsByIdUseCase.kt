package dev.maxsiomin.prodhse.feature.home.domain.use_case.places

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import javax.inject.Inject

internal class GetPlaceDetailsByIdUseCase @Inject constructor(private val placesRepo: PlacesRepository) {

    suspend operator fun invoke(fsqId: String): Resource<PlaceDetails, NetworkError> {
        return placesRepo.getPlaceDetails(fsqId = fsqId)
    }

}
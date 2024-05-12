package dev.maxsiomin.prodhse.feature.home.domain.use_case.places

import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetPhotosOfPlaceUseCase @Inject constructor(private val placesRepo: PlacesRepository) {

    suspend operator fun invoke(
        fsqId: String
    ): Resource<List<Photo>, DataError> = withContext(Dispatchers.IO) {
        return@withContext placesRepo.getPhotos(fsqId = fsqId)
    }

}
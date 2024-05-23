package dev.maxsiomin.prodhse.feature.home.domain.use_case.places

import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class BatchAddPhotosToPlacesUseCase @Inject constructor(
    private val getPhotosOfPlaceUseCase: GetPhotosOfPlaceUseCase,
    private val dispatchers: DispatcherProvider,
) {

    suspend operator fun invoke(
        places: List<Place>,
        placeholderUrl: String
    ): List<Place> = withContext(dispatchers.io) {
        val placesWithPhotos = places.map { currPlace ->
            async {
                var photo: Photo? = null
                val photosResource = getPhotosOfPlaceUseCase(currPlace.fsqId)
                when (photosResource) {
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        photo = photosResource.data.firstOrNull()
                    }
                }
                currPlace.copy(photoUrl = photo?.url ?: placeholderUrl)
            }
        }.awaitAll()
        return@withContext placesWithPhotos
    }

}
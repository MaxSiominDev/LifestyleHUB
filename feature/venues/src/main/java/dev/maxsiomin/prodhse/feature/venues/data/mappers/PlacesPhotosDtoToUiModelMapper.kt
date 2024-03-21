package dev.maxsiomin.prodhse.feature.venues.data.mappers

import dev.maxsiomin.prodhse.feature.venues.data.dto.place_photos.PlacePhotosResponse
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel

class PlacesPhotosDtoToUiModelMapper : (PlacePhotosResponse) -> List<PhotoModel> {

    override fun invoke(response: PlacePhotosResponse): List<PhotoModel> {
        return response.mapNotNull {
            val url = "${it.prefix}original${it.suffix}"
            PhotoModel(url)
        }
    }

}
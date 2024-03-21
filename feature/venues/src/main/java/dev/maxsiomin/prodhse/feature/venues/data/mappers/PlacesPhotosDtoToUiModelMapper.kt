package dev.maxsiomin.prodhse.feature.venues.data.mappers

import dev.maxsiomin.prodhse.feature.venues.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel

class PlacesPhotosDtoToUiModelMapper : (List<PlacePhotosResponseItem>) -> List<PhotoModel> {

    override fun invoke(response: List<PlacePhotosResponseItem>): List<PhotoModel> {
        return response.map {
            val url = "${it.prefix}original${it.suffix}"
            PhotoModel(url)
        }
    }

}
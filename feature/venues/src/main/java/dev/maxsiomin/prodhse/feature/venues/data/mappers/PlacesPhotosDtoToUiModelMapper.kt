package dev.maxsiomin.prodhse.feature.venues.data.mappers

import dev.maxsiomin.prodhse.feature.venues.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel

internal class PlacesPhotosDtoToUiModelMapper : (List<PlacePhotosResponseItem>, String) -> List<PhotoModel> {

    override fun invoke(response: List<PlacePhotosResponseItem>, fsqId: String): List<PhotoModel> {
        return response.map {
            val url = "${it.prefix}original${it.suffix}"
            PhotoModel(url = url, id = fsqId)
        }
    }

}
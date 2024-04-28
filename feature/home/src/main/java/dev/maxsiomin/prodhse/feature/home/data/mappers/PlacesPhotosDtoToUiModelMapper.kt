package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.prodhse.feature.home.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo

internal class PlacesPhotosDtoToUiModelMapper : (List<PlacePhotosResponseItem>, String) -> List<Photo> {

    override fun invoke(response: List<PlacePhotosResponseItem>, fsqId: String): List<Photo> {
        return response.map {
            val url = "${it.prefix}original${it.suffix}"
            Photo(url = url, id = fsqId)
        }
    }

}
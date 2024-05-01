package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.prodhse.feature.home.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import javax.inject.Inject

internal class PlacePhotosMapper @Inject constructor() :
    ToDomainMapper<@JvmSuppressWildcards Pair<PlacePhotosResponseItem, FsqId>, Photo> {

    override fun toDomain(data: Pair<PlacePhotosResponseItem, FsqId>): Photo {
        val response = data.first
        val fsqId = data.second
        val url = "${response.prefix}original${response.suffix}"
        return Photo(url = url, id = fsqId)
    }

}

typealias FsqId = String

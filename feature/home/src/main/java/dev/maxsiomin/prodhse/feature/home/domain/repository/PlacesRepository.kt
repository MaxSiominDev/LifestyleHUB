package dev.maxsiomin.prodhse.feature.home.domain.repository

import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.model.Place

internal interface PlacesRepository {

    suspend fun getPlacesNearby(
        lat: String,
        lon: String,
        lang: String
    ): Resource<List<Place>, NetworkError>

    suspend fun getPhotos(
        fsqId: String,
    ): Resource<List<Photo>, NetworkError>

    suspend fun getPlaceDetails(
        fsqId: String,
    ): Resource<PlaceDetails, DataError>

}
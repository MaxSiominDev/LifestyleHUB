package dev.maxsiomin.prodhse.feature.home.domain.repository

import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails

internal interface PlacesRepository {

    suspend fun getPlacesNearby(
        lat: String,
        lon: String,
        lang: String
    ): Resource<List<Place>, DataError>

    suspend fun getPhotos(
        fsqId: String,
    ): Resource<List<Photo>, DataError>

    suspend fun getPlaceDetails(
        fsqId: String,
    ): Resource<PlaceDetails, DataError>

}
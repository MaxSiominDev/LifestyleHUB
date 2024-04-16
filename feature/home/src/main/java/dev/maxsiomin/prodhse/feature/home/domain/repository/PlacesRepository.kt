package dev.maxsiomin.prodhse.feature.home.domain.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.home.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.home.domain.PlaceModel
import kotlinx.coroutines.flow.Flow

internal interface PlacesRepository {

    suspend fun getPlacesNearby(
        lat: String,
        lon: String,
        lang: String
    ): Flow<Resource<List<PlaceModel>, NetworkError>>

    suspend fun getPhotos(
        id: String,
    ): Flow<Resource<List<PhotoModel>, NetworkError>>

    suspend fun getPlaceDetails(
        id: String,
    ): Flow<Resource<PlaceDetailsModel, NetworkError>>

}
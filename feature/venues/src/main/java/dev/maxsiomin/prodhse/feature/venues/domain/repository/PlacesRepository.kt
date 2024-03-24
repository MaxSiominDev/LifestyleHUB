package dev.maxsiomin.prodhse.feature.venues.domain.repository

import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel
import kotlinx.coroutines.flow.Flow

internal interface PlacesRepository {

    suspend fun getPlacesNearby(
        lat: String,
        lon: String,
        lang: String
    ): Flow<Resource<List<PlaceModel>>>

    suspend fun getPhotos(
        id: String,
    ): Flow<Resource<List<PhotoModel>>>

    suspend fun getPlaceDetails(
        id: String,
    ): Flow<Resource<PlaceDetailsModel>>

}
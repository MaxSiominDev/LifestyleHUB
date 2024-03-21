package dev.maxsiomin.prodhse.feature.venues.domain.repository

import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby.Result
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel
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

}
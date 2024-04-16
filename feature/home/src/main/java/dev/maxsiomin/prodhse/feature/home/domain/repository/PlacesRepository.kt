package dev.maxsiomin.prodhse.feature.home.domain.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.Photo
import dev.maxsiomin.prodhse.feature.home.domain.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.Place
import kotlinx.coroutines.flow.Flow

internal interface PlacesRepository {

    suspend fun getPlacesNearby(
        lat: String,
        lon: String,
        lang: String
    ): Flow<Resource<List<Place>, NetworkError>>

    suspend fun getPhotos(
        id: String,
    ): Flow<Resource<List<Photo>, NetworkError>>

    suspend fun getPlaceDetails(
        id: String,
    ): Flow<Resource<PlaceDetails, NetworkError>>

}
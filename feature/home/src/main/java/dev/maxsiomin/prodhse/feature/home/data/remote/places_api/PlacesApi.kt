package dev.maxsiomin.prodhse.feature.home.data.remote.places_api

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby.PlacesResponse

internal interface PlacesApi {

    suspend fun getPlaces(
        lat: String,
        lon: String,
        lang: String
    ): Resource<PlacesResponse, NetworkError>

    suspend fun getPhotos(fsqId: String): Resource<List<PlacePhotosResponseItem>, NetworkError>

    suspend fun getPlaceDetails(fsqId: String): Resource<PlaceDetailsResponse, NetworkError>

}
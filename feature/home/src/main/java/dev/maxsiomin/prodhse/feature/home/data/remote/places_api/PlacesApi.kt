package dev.maxsiomin.prodhse.feature.home.data.remote.places_api

import dev.maxsiomin.prodhse.core.util.ResponseWithException
import dev.maxsiomin.prodhse.feature.home.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby.PlacesResponse

internal interface PlacesApi {

    suspend fun getPlaces(
        lat: String,
        lon: String,
        lang: String
    ): ResponseWithException<PlacesResponse, Exception>

    suspend fun getPhotos(id: String): ResponseWithException<List<PlacePhotosResponseItem>, Exception>

    suspend fun getPlaceDetails(id: String): ResponseWithException<PlaceDetailsResponse, Exception>

}
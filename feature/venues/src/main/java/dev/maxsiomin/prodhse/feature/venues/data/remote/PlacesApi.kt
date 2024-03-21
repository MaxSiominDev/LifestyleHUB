package dev.maxsiomin.prodhse.feature.venues.data.remote

import dev.maxsiomin.prodhse.core.ResponseWithException
import dev.maxsiomin.prodhse.feature.venues.data.dto.place_photos.PlacePhotosResponse
import dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby.PlacesResponse

internal interface PlacesApi {

    suspend fun getPlaces(
        lat: String,
        lon: String,
        lang: String
    ): ResponseWithException<PlacesResponse, Exception>

    suspend fun getPhotos(id: String): ResponseWithException<PlacePhotosResponse, Exception>

}
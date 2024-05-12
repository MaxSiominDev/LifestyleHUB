package dev.maxsiomin.prodhse.feature.home.data.remote.places_api

import dev.maxsiomin.common.data.safeGet
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.ApiKeys
import dev.maxsiomin.prodhse.feature.home.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby.PlacesResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

internal class PlacesApiImpl @Inject constructor(private val client: HttpClient) : PlacesApi {

    override suspend fun getPlaces(
        lat: String,
        lon: String,
        lang: String
    ): Resource<PlacesResponse, NetworkError> {
        return client.safeGet {
            url(HttpRoutes.GET_PLACES_NEARBY)
            parameter("ll", "$lat,$lon")
            parameter("sort", "DISTANCE")
            parameter("limit", 50)
            header("Accept", "application/json")
            header("Authorization", ApiKeys.FOURS_SQUARE)
            header("Accept-Language", lang)
        }
    }

    override suspend fun getPhotos(fsqId: String): Resource<List<PlacePhotosResponseItem>, NetworkError> {
        return client.safeGet {
            url(HttpRoutes.getPlacePhotosUrl(fsqId = fsqId))
            parameter("sort", "NEWEST")
            header("Accept", "application/json")
            header("Authorization", ApiKeys.FOURS_SQUARE)
        }
    }

    override suspend fun getPlaceDetails(fsqId: String): Resource<PlaceDetailsResponse, NetworkError> {
        return client.safeGet {
            url(HttpRoutes.getPlaceDetailsUrl(fsqId = fsqId))
            parameter("fields", placeDetailsFields)
            header("Accept", "application/json")
            header("Authorization", ApiKeys.FOURS_SQUARE)
        }
    }

    companion object {
        // Place details fields
        private const val HOURS = "hours"
        private const val RATING = "rating"
        private const val WEBSITE = "website"
        private const val VERIFIED = "verified"
        private const val FSQ_ID = "fsq_id"
        private const val NAME = "name"
        private const val LOCATION = "location"
        private const val CATEGORIES = "categories"
        private const val PHOTOS = "photos"
        private const val PHONE = "tel"
        private const val EMAIL = "email"

        private val placeDetailsFields = listOf(
            HOURS,
            RATING,
            WEBSITE,
            VERIFIED,
            FSQ_ID,
            NAME,
            LOCATION,
            CATEGORIES,
            PHOTOS,
            PHONE,
            EMAIL
        ).joinToString(separator = ",")
    }

}
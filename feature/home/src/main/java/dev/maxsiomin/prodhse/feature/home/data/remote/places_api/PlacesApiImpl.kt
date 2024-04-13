package dev.maxsiomin.prodhse.feature.home.data.remote.places_api

import dev.maxsiomin.prodhse.core.ApiKeys
import dev.maxsiomin.prodhse.core.util.ResponseWithException
import dev.maxsiomin.prodhse.feature.home.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby.PlacesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import timber.log.Timber
import javax.inject.Inject

internal class PlacesApiImpl @Inject constructor(private val client: HttpClient) : PlacesApi {

    override suspend fun getPlaces(
        lat: String,
        lon: String,
        lang: String
    ): ResponseWithException<PlacesResponse, Exception> {
        try {
            val response: PlacesResponse? = client.get {
                url(HttpRoutes.GET_PLACES_NEARBY)
                parameter("ll", "$lat,$lon")
                parameter("sort", "DISTANCE")
                parameter("limit", 50)
                header("Accept", "application/json")
                header("Authorization", ApiKeys.FOURS_SQUARE)
                header("Accept-Language", lang)
            }.body()
            if (response == null) {
                Timber.e("Response is null")
                return ResponseWithException(null, Exception("Response is null"))
            }
            return ResponseWithException(response, null)
        } catch (e: RedirectResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: ClientRequestException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: ServerResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: Exception) {
            Timber.e(e.message)
            return ResponseWithException(null, e)
        }
    }

    override suspend fun getPhotos(id: String): ResponseWithException<List<PlacePhotosResponseItem>, Exception> {
        try {
            val response: List<PlacePhotosResponseItem>? = client.get {
                url(HttpRoutes.getPlacePhotosUrl(fsqId = id))
                parameter("sort", "NEWEST")
                header("Accept", "application/json")
                header("Authorization", ApiKeys.FOURS_SQUARE)
            }.body()
            if (response == null) {
                Timber.e("Response is null")
                return ResponseWithException(null, Exception("Response is null"))
            }
            return ResponseWithException(response, null)
        } catch (e: RedirectResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: ClientRequestException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: ServerResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: Exception) {
            Timber.e(e.message)
            return ResponseWithException(null, e)
        }
    }

    override suspend fun getPlaceDetails(id: String): ResponseWithException<PlaceDetailsResponse, Exception> {
        try {
            val response: PlaceDetailsResponse? = client.get {
                url(HttpRoutes.getPlaceDetailsUrl(fsqId = id))
                parameter("fields", placeDetailsFields)
                header("Accept", "application/json")
                header("Authorization", ApiKeys.FOURS_SQUARE)
            }.body()
            if (response == null) {
                Timber.e("Response is null")
                return ResponseWithException(null, Exception("Response is null"))
            }
            return ResponseWithException(response, null)
        } catch (e: RedirectResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: ClientRequestException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: ServerResponseException) {
            Timber.e(e.response.status.description)
            return ResponseWithException(null, e)
        } catch (e: Exception) {
            Timber.e(e.message)
            return ResponseWithException(null, e)
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
package dev.maxsiomin.prodhse.feature.home.data.repository

import android.content.SharedPreferences
import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.LocalError
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.place_photos.PlacePhotosResponseItem
import dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby.Result
import dev.maxsiomin.prodhse.feature.home.data.mappers.FsqId
import dev.maxsiomin.prodhse.feature.home.data.remote.places_api.PlacesApi
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class PlacesRepositoryImpl @Inject constructor(
    private val api: PlacesApi,
    private val prefs: SharedPreferences,
    private val placeMapper: ToDomainMapper<Result, Place?>,
    private val placeDetailsMapper: ToDomainMapper<PlaceDetailsResponse, PlaceDetails?>,
    private val placePhotosMapper: ToDomainMapper<Pair<PlacePhotosResponseItem, FsqId>, Photo>,
) : PlacesRepository {

    override suspend fun getPlacesNearby(
        lat: String,
        lon: String,
        lang: String
    ): Resource<List<Place>, DataError> {
        val apiResponse = api.getPlaces(lat = lat, lon = lon, lang = lang)
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                apiResponse.data.results?.filterNotNull()?.mapNotNull { placeMapper.toDomain(it) }
                    ?.let {
                        Resource.Success(it)
                    } ?: Resource.Error(NetworkError.EmptyResponse)
            }
        }
    }

    override suspend fun getPhotos(fsqId: String): Resource<List<Photo>, DataError> {
        val apiResponse = api.getPhotos(fsqId = fsqId)
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                val remoteData: List<Photo> = apiResponse.data.map { item: PlacePhotosResponseItem ->
                    placePhotosMapper.toDomain(item to fsqId)
                }
                Resource.Success(remoteData)
            }
        }
    }

    override suspend fun getPlaceDetails(fsqId: String): Resource<PlaceDetails, DataError> {
        val localData = getPlaceDetailsFromSharedPrefs(fsqId)
        val currentMillis = System.currentTimeMillis()
        if (localData != null && currentMillis - localData.timeUpdated < CACHE_EXPIRATION_PERIOD) {
            return Resource.Success(localData)
        }

        val apiResponse = api.getPlaceDetails(fsqId = fsqId)
        when (apiResponse) {
            is Resource.Error -> return Resource.Error(apiResponse.error)
            is Resource.Success -> {
                val placeDetails = placeDetailsMapper.toDomain(apiResponse.data)
                if (placeDetails == null) {
                    return Resource.Error(NetworkError.EmptyResponse)
                }
                savePlaceDetailsToSharedPrefs(placeDetails)
                val updatedLocalData = getPlaceDetailsFromSharedPrefs(fsqId)
                if (updatedLocalData == null) {
                    return Resource.Error(LocalError.Unknown(null))
                } else {
                    return Resource.Success(updatedLocalData)
                }
            }
        }
    }

    private fun savePlaceDetailsToSharedPrefs(place: PlaceDetails) {
        prefs.edit().apply {
            val jsonString = Json.encodeToString(PlaceDetails.serializer(), place)
            val key = getPlacePrefsKey(place.fsqId)
            putString(key, jsonString)
        }.apply()
    }

    private fun getPlaceDetailsFromSharedPrefs(id: String): PlaceDetails? {
        val key = getPlacePrefsKey(id)
        val jsonString = prefs.getString(key, null) ?: return null
        return Json.decodeFromString(PlaceDetails.serializer(), jsonString)
    }

    companion object {
        private fun getPlacePrefsKey(id: String) = "venue/$id"

        /** 6 hours */
        private const val CACHE_EXPIRATION_PERIOD = 1000 * 60 * 60 * 6
    }

}
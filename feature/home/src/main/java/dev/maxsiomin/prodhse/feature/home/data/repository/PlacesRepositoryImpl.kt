package dev.maxsiomin.prodhse.feature.home.data.repository

import android.content.SharedPreferences
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlaceDetailsDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlacesDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlacesPhotosDtoToUiModelMapper
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
) : PlacesRepository {

    override suspend fun getPlacesNearby(
        lat: String,
        lon: String,
        lang: String
    ): Resource<List<Place>, NetworkError> {
        val apiResponse = api.getPlaces(lat = lat, lon = lon, lang = lang)
        val mapper = PlacesDtoToUiModelMapper()
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                apiResponse.data.results?.filterNotNull()?.mapNotNull { mapper.invoke(it) }
                    ?.let {
                        Resource.Success(it)
                    } ?: Resource.Error(NetworkError.EmptyResponse)
            }
        }
    }

    override suspend fun getPhotos(id: String): Resource<List<Photo>, NetworkError> {
        val apiResponse = api.getPhotos(id = id)
        val mapper = PlacesPhotosDtoToUiModelMapper()
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                val remoteData = mapper(apiResponse.data, id)
                Resource.Success(remoteData)
            }
        }
    }

    override suspend fun getPlaceDetails(id: String): Resource<PlaceDetails, NetworkError> {
        val localData = getPlaceDetailsFromSharedPrefs(id)
        val currentMillis = System.currentTimeMillis()
        if (localData != null && currentMillis - localData.timeUpdated < CACHE_EXPIRATION_PERIOD) {
            return Resource.Success(localData)
        }

        val apiResponse = api.getPlaceDetails(id = id)
        val mapper = PlaceDetailsDtoToUiModelMapper()
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                apiResponse.data.let(mapper)?.let {
                    savePlaceDetailsToSharedPrefs(it)
                    Resource.Success(it)
                } ?: Resource.Error(NetworkError.EmptyResponse)
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
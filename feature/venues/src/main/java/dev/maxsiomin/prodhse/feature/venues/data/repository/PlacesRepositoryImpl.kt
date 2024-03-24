package dev.maxsiomin.prodhse.feature.venues.data.repository

import android.content.SharedPreferences
import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.core.asResult
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlaceDetailsDtoToUoModelMapper
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlacesDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlacesPhotosDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.venues.data.remote.PlacesApi
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<Resource<List<PlaceModel>>> {
        return flow {
            val apiResponse = api.getPlaces(lat = lat, lon = lon, lang = lang)
            val mapper = PlacesDtoToUiModelMapper()
            val remoteData =
                apiResponse.response?.results?.filterNotNull()?.mapNotNull { mapper.invoke(it) }
            if (remoteData != null) {
                emit(remoteData)
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

    override suspend fun getPhotos(id: String): Flow<Resource<List<PhotoModel>>> {
        return flow {
            val apiResponse = api.getPhotos(id = id)
            val mapper = PlacesPhotosDtoToUiModelMapper()
            val remoteData = apiResponse.response?.let { mapper(it, id) }
            if (remoteData != null) {
                emit(remoteData)
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

    override suspend fun getPlaceDetails(id: String): Flow<Resource<PlaceDetailsModel>> {
        return flow {
            val localData = getPlaceDetailsFromSharedPrefs(id)
            val currentMillis = System.currentTimeMillis()
            if (localData != null && currentMillis - localData.timeUpdated < CACHE_EXPIRATION_PERIOD) {
                emit(localData)
                return@flow
            }

            val apiResponse = api.getPlaceDetails(id = id)
            val mapper = PlaceDetailsDtoToUoModelMapper()
            val remoteData = apiResponse.response?.let(mapper)
            if (remoteData != null) {
                emit(remoteData)
                savePlaceDetailsToSharedPrefs(place = remoteData)
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

    private fun savePlaceDetailsToSharedPrefs(place: PlaceDetailsModel) {
        prefs.edit().apply {
            val jsonString = Json.encodeToString(PlaceDetailsModel.serializer(), place)
            val key = getPlacePrefsKey(place.id)
            putString(key, jsonString)
        }.apply()
    }

    private fun getPlaceDetailsFromSharedPrefs(id: String): PlaceDetailsModel? {
        val key = getPlacePrefsKey(id)
        val jsonString = prefs.getString(key, null) ?: return null
        return Json.decodeFromString(PlaceDetailsModel.serializer(), jsonString)
    }

    companion object {
        private fun getPlacePrefsKey(id: String) = "venue/$id"

        /** 6 hours */
        private const val CACHE_EXPIRATION_PERIOD = 1000 * 60 * 60 * 6
    }

}
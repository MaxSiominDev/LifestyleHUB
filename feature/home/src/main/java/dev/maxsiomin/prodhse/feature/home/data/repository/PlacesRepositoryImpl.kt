package dev.maxsiomin.prodhse.feature.home.data.repository

import android.content.SharedPreferences
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlaceDetailsDtoToUoModelMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlacesDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlacesPhotosDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.home.data.remote.places_api.PlacesApi
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
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
    ): Flow<Resource<List<Place>, NetworkError>> {
        return flow {
            val apiResponse = api.getPlaces(lat = lat, lon = lon, lang = lang)
            val mapper = PlacesDtoToUiModelMapper()
            when (apiResponse) {
                is Resource.Error -> emit(Resource.Error(apiResponse.error))
                is Resource.Success -> {
                    apiResponse.data.results?.filterNotNull()?.mapNotNull { mapper.invoke(it) }
                        ?.let {
                            emit(Resource.Success(it))
                        } ?: emit(Resource.Error(NetworkError.EmptyResponse))
                }
            }
        }
    }

    override suspend fun getPhotos(id: String): Flow<Resource<List<Photo>, NetworkError>> {
        return flow {
            val apiResponse = api.getPhotos(id = id)
            val mapper = PlacesPhotosDtoToUiModelMapper()
            when (apiResponse) {
                is Resource.Error -> emit(Resource.Error(apiResponse.error))
                is Resource.Success -> {
                    val remoteData = mapper(apiResponse.data, id)
                    emit(Resource.Success(remoteData))
                }
            }
        }
    }

    override suspend fun getPlaceDetails(id: String): Flow<Resource<PlaceDetails, NetworkError>> {
        return flow {
            val localData = getPlaceDetailsFromSharedPrefs(id)
            val currentMillis = System.currentTimeMillis()
            if (localData != null && currentMillis - localData.timeUpdated < CACHE_EXPIRATION_PERIOD) {
                emit(Resource.Success(localData))
                return@flow
            }

            val apiResponse = api.getPlaceDetails(id = id)
            val mapper = PlaceDetailsDtoToUoModelMapper()
            when (apiResponse) {
                is Resource.Error -> emit(Resource.Error(apiResponse.error))
                is Resource.Success -> {
                    apiResponse.data.let(mapper)?.let {
                        emit(Resource.Success(it))
                    } ?: emit(Resource.Error(NetworkError.EmptyResponse))
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
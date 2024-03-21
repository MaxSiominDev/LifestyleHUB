package dev.maxsiomin.prodhse.feature.venues.data.repository

import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.core.asResult
import dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby.Result
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlacesDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlacesPhotosDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.venues.data.remote.PlacesApi
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class PlacesRepositoryImpl @Inject constructor(
    private val api: PlacesApi
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
            val remoteData = apiResponse.response?.let(mapper)
            if (remoteData != null) {
                emit(remoteData)
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

}
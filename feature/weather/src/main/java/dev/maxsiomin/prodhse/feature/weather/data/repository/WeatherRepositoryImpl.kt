package dev.maxsiomin.prodhse.feature.weather.data.repository

import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.core.extensions.asResult
import dev.maxsiomin.prodhse.feature.weather.data.mappers.WeatherDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.weather.data.remote.WeatherApi
import dev.maxsiomin.prodhse.feature.weather.domain.WeatherModel
import dev.maxsiomin.prodhse.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi) : WeatherRepository {

    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String,
    ): Flow<Resource<WeatherModel>> {
        return flow {
            val apiResponse = api.getCurrentWeather(lat = lat, lon = lon, lang = lang)
            val remoteData = apiResponse.response
            if (remoteData != null) {
                val mapper = WeatherDtoToUiModelMapper()
                emit(mapper.invoke(remoteData, null))
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

}
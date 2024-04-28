package dev.maxsiomin.prodhse.feature.home.data.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.mappers.WeatherDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.home.data.remote.weather_api.WeatherApi
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi) :
    WeatherRepository {

    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String,
    ): Resource<Weather, NetworkError> {
        val apiResponse = api.getCurrentWeather(lat = lat, lon = lon, lang = lang)
        val mapper = WeatherDtoToUiModelMapper()
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                val remoteData = mapper.invoke(apiResponse.data)
                Resource.Success(remoteData)
            }
        }
    }

}
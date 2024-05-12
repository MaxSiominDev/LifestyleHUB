package dev.maxsiomin.prodhse.feature.home.data.repository

import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.home.data.mappers.WeatherMapper
import dev.maxsiomin.prodhse.feature.home.data.remote.weather_api.WeatherApi
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val weatherMapper: ToDomainMapper<CurrentWeatherResponse, Weather>,
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String,
    ): Resource<Weather, DataError> {
        val apiResponse = api.getCurrentWeather(lat = lat, lon = lon, lang = lang)
        return when (apiResponse) {
            is Resource.Error -> Resource.Error(apiResponse.error)
            is Resource.Success -> {
                val remoteData = weatherMapper.toDomain(apiResponse.data)
                Resource.Success(remoteData)
            }
        }
    }

    override fun getDefaultWeather(): Weather {
        return weatherMapper.toDomain(CurrentWeatherResponse())
    }

}
package dev.maxsiomin.prodhse.feature.weather.domain.repository

import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.feature.weather.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.weather.domain.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String,
    ): Flow<Resource<WeatherModel>>

}
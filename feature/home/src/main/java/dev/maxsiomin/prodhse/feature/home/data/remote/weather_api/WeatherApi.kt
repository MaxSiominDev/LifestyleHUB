package dev.maxsiomin.prodhse.feature.home.data.remote.weather_api

import dev.maxsiomin.common.domain.NetworkError
import dev.maxsiomin.common.domain.Resource
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse

internal interface WeatherApi {

    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String
    ): Resource<CurrentWeatherResponse, NetworkError>

}
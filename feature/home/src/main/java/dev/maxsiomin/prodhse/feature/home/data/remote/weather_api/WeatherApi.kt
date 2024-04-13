package dev.maxsiomin.prodhse.feature.home.data.remote.weather_api

import dev.maxsiomin.prodhse.core.util.ResponseWithException
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse

internal interface WeatherApi {

    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String
    ): ResponseWithException<dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse, Exception>

}
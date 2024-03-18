package dev.maxsiomin.prodhse.feature.weather.data.remote

import dev.maxsiomin.prodhse.core.ResponseWithMessage
import dev.maxsiomin.prodhse.feature.weather.data.dto.current_weather_response.CurrentWeatherResponse

internal interface WeatherApi {

    suspend fun getCurrentWeather(lat: String, lon: String, lang: String): ResponseWithMessage<CurrentWeatherResponse, Exception>

}
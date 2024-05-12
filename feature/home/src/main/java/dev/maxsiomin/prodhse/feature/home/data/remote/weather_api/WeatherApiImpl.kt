package dev.maxsiomin.prodhse.feature.home.data.remote.weather_api

import dev.maxsiomin.prodhse.core.ApiKeys
import dev.maxsiomin.common.data.safeGet
import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

internal class WeatherApiImpl @Inject constructor(private val client: HttpClient) : WeatherApi {

    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String
    ): Resource<CurrentWeatherResponse, NetworkError> {
        return client.safeGet {
            url(HttpRoutes.CURRENT_WEATHER)
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("lang", lang)
            parameter("units", "metric")
            parameter("appid", ApiKeys.OPEN_WEATHER_MAP)
        }

    }
}
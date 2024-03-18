package dev.maxsiomin.prodhse.feature.weather.data.repository

import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.core.asResult
import dev.maxsiomin.prodhse.feature.weather.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.weather.data.remote.WeatherApi
import dev.maxsiomin.prodhse.feature.weather.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi) : WeatherRepository {

    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String
    ): Flow<Resource<CurrentWeatherResponse>> {
        return flow {
            val apiResponse = api.getCurrentWeather(lat = lat, lon = lon, lang = lang)
            val remoteData = apiResponse.response
            if (remoteData != null) {
                emit(remoteData)
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

}
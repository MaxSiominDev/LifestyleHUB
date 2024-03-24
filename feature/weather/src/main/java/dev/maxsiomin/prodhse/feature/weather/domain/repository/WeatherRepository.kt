package dev.maxsiomin.prodhse.feature.weather.domain.repository

import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.weather.domain.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        lang: String,
    ): Flow<Resource<WeatherModel>>

}
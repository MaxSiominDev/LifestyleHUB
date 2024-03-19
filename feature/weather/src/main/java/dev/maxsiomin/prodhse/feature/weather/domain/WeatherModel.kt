package dev.maxsiomin.prodhse.feature.weather.domain

import androidx.compose.ui.graphics.Color

internal data class WeatherModel(
    val city: String,
    val temperatureInfo: TemperatureInfo,
    val weatherCondition: WeatherCondition,
    val backgroundColor: Color,
)

internal data class WeatherCondition(
    val name: String,
    val iconUrl: String?,
)

internal data class TemperatureInfo(
    val current: String,
    val range: String,
    val feelsLike: String,
)
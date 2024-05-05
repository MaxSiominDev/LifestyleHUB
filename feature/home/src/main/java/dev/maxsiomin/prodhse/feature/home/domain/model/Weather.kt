package dev.maxsiomin.prodhse.feature.home.domain.model

internal data class Weather(
    val city: String,
    val temperatureInfo: TemperatureInfo,
    val weatherCondition: WeatherCondition,
    val date: String,
)

internal data class WeatherCondition(
    val name: String,
    val iconUrl: String?,
    val isNight: Boolean,
)

internal data class TemperatureInfo(
    val current: String,
    val range: String,
    val feelsLike: String,
)
package dev.maxsiomin.prodhse.feature.home.domain

data class WeatherModel(
    val city: String,
    val temperatureInfo: TemperatureInfo,
    val weatherCondition: WeatherCondition,
    val date: String,
) {}

data class WeatherCondition(
    val name: String,
    val iconUrl: String?,
    val isNight: Boolean,
)

data class TemperatureInfo(
    val current: String,
    val range: String,
    val feelsLike: String,
)
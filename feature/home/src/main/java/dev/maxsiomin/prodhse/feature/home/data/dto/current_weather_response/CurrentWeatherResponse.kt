package dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class CurrentWeatherResponse(
    @SerialName("coord")
    val coord: Coord? = null,
    @SerialName("weather")
    val weather: List<Weather?>? = null,
    @SerialName("base")
    val base: String? = null,
    @SerialName("main")
    val main: Main? = null,
    @SerialName("visibility")
    val visibility: Int? = null,
    @SerialName("wind")
    val wind: Wind? = null,
    @SerialName("rain")
    val rain: Rain? = null,
    @SerialName("clouds")
    val clouds: Clouds? = null,
    @SerialName("dt")
    val dt: Int? = null,
    @SerialName("sys")
    val sys: Sys? = null,
    @SerialName("timezone")
    val timezone: Int? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("cod")
    val cod: Int? = null,
)
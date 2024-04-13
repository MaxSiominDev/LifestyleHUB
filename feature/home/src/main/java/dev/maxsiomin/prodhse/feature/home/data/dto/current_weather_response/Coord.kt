package dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class Coord(
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("lat")
    val lat: Double? = null,
)
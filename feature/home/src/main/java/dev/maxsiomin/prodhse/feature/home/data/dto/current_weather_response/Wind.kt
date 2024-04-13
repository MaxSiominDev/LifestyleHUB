package dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Wind(
    @SerialName("speed")
    val speed: Double? = null,
    @SerialName("deg")
    val deg: Int? = null,
    @SerialName("gust")
    val gust: Double? = null,
)
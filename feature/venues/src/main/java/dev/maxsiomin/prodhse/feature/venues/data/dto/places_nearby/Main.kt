package dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Main(
    @SerialName("latitude")
    val latitude: Double? = null,
    @SerialName("longitude")
    val longitude: Double? = null
)
package dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Geocodes(
    @SerialName("main")
    val main: Main? = null,
    @SerialName("roof")
    val roof: Roof? = null
)
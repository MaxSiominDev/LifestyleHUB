package dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Circle(
    @SerialName("center")
    val center: Center? = null,
    @SerialName("radius")
    val radius: Int? = null
)
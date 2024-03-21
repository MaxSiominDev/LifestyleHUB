package dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class RelatedPlaces(
    @SerialName("parent")
    val parent: Parent? = null
)
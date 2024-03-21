package dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class PlacesResponse(
    @SerialName("results")
    val results: List<Result?>? = null,
    @SerialName("context")
    val context: Context? = null
)
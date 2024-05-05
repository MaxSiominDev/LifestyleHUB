package dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Context(
    @SerialName("geo_bounds")
    val geoBounds: GeoBounds? = null
)
package dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Chain(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null
)
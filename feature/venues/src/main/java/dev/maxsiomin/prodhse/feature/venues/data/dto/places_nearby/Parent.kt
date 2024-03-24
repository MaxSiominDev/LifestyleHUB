package dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Parent(
    @SerialName("fsq_id")
    val fsqId: String? = null,
    @SerialName("name")
    val name: String? = null
)
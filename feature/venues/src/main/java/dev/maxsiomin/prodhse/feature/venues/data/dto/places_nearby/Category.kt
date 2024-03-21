package dev.maxsiomin.prodhse.feature.venues.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Category(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("icon")
    val icon: Icon? = null
)
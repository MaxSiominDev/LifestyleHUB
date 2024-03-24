package dev.maxsiomin.prodhse.feature.venues.data.dto.place_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Photo(
    @SerialName("id")
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("prefix")
    val prefix: String? = null,
    @SerialName("suffix")
    val suffix: String? = null,
    @SerialName("width")
    val width: Int? = null,
    @SerialName("height")
    val height: Int? = null
)
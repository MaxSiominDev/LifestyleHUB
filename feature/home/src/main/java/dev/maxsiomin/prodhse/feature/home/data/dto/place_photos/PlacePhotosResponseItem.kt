package dev.maxsiomin.prodhse.feature.home.data.dto.place_photos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class PlacePhotosResponseItem(
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
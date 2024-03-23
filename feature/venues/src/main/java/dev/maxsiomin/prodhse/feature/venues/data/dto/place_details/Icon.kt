package dev.maxsiomin.prodhse.feature.venues.data.dto.place_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Icon(
    @SerialName("prefix")
    val prefix: String? = null,
    @SerialName("suffix")
    val suffix: String? = null
)
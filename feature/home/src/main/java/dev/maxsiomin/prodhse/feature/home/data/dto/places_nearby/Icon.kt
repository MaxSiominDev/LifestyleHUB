package dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Icon(
    @SerialName("prefix")
    val prefix: String? = null,
    @SerialName("suffix")
    val suffix: String? = null
)
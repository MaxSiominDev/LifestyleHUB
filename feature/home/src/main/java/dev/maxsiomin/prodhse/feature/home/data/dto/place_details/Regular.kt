package dev.maxsiomin.prodhse.feature.home.data.dto.place_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Regular(
    @SerialName("close")
    val close: String? = null,
    @SerialName("day")
    val day: Int? = null,
    @SerialName("open")
    val `open`: String? = null
)
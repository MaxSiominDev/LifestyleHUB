package dev.maxsiomin.prodhse.feature.auth.data.dto.bored

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class BoredApiResponse(
    @SerialName("activity")
    val activity: String? = null,
    @SerialName("accessibility")
    val accessibility: Double? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("participants")
    val participants: Int? = null,
    @SerialName("price")
    val price: Double? = null,
    @SerialName("link")
    val link: String? = null,
    @SerialName("key")
    val key: String? = null
)
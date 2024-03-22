package dev.maxsiomin.prodhse.feature.auth.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Id(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val value: String
)
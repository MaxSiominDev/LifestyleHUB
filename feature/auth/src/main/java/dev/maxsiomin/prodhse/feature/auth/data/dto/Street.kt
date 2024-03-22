package dev.maxsiomin.prodhse.feature.auth.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Street(
    @SerialName("number")
    val number: Int,
    @SerialName("name")
    val name: String
)
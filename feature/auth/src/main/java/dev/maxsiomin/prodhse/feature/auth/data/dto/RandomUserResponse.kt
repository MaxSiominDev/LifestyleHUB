package dev.maxsiomin.prodhse.feature.auth.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class RandomUserResponse(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("info")
    val info: Info
)
package dev.maxsiomin.prodhse.feature.auth.data.dto.random_user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class RandomUserResponse(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("info")
    val info: Info
)
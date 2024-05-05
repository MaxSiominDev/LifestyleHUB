package dev.maxsiomin.prodhse.feature.auth.data.dto.random_user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Info(
    @SerialName("seed")
    val seed: String,
    @SerialName("results")
    val results: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("version")
    val version: String
)
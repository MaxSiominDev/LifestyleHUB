package dev.maxsiomin.prodhse.feature.auth.data.dto.random_user


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Registered(
    @SerialName("date")
    val date: String,
    @SerialName("age")
    val age: Int
)
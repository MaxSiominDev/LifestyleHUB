package dev.maxsiomin.prodhse.feature.auth.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Dob(
    @SerialName("date")
    val date: String,
    @SerialName("age")
    val age: Int
)
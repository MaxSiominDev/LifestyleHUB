package dev.maxsiomin.prodhse.feature.auth.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Timezone(
    @SerialName("offset")
    val offset: String,
    @SerialName("description")
    val description: String
)
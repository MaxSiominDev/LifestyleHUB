package dev.maxsiomin.prodhse.feature.auth.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Location(
    @SerialName("street")
    val street: Street,
    @SerialName("city")
    val city: String,
    @SerialName("state")
    val state: String,
    @SerialName("country")
    val country: String,
    @SerialName("coordinates")
    val coordinates: Coordinates,
    @SerialName("timezone")
    val timezone: Timezone
)
package dev.maxsiomin.prodhse.feature.venues.data.dto.place_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class CategoryX(
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("name")
    val name: String? = "",
    @SerialName("short_name")
    val shortName: String? = "",
    @SerialName("plural_name")
    val pluralName: String? = "",
    @SerialName("icon")
    val icon: Icon? = Icon()
)
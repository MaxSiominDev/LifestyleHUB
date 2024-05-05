package dev.maxsiomin.prodhse.feature.home.data.dto.place_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Category(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("short_name")
    val shortName: String? = null,
    @SerialName("plural_name")
    val pluralName: String? = null,
    @SerialName("icon")
    val icon: Icon? = null
)
package dev.maxsiomin.prodhse.feature.venues.data.dto.place_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Parent(
    @SerialName("fsq_id")
    val fsqId: String? = "",
    @SerialName("categories")
    val categories: List<CategoryX>? = listOf(),
    @SerialName("name")
    val name: String? = ""
)
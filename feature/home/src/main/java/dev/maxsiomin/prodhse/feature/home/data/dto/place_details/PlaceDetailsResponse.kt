package dev.maxsiomin.prodhse.feature.home.data.dto.place_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class PlaceDetailsResponse(
    @SerialName("fsq_id")
    val fsqId: String? = null,
    @SerialName("categories")
    val categories: List<Category?>? = null,
    @SerialName("hours")
    val hours: Hours? = null,
    @SerialName("location")
    val location: Location? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("photos")
    val photos: List<Photo?>? = null,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("verified")
    val verified: Boolean? = null,
    @SerialName("website")
    val website: String? = null,
    @SerialName("tel")
    val phone: String? = null,
    @SerialName("email")
    val email: String? = null,
)
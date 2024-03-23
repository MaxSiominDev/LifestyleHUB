package dev.maxsiomin.prodhse.feature.venues.data.dto.place_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class PlaceDetailsResponse(
    @SerialName("fsq_id")
    val fsqId: String? = "",
    @SerialName("categories")
    val categories: List<Category>? = listOf(),
    @SerialName("closed_bucket")
    val closedBucket: String? = "",
    @SerialName("geocodes")
    val geocodes: Geocodes? = Geocodes(),
    @SerialName("link")
    val link: String? = "",
    @SerialName("location")
    val location: Location? = Location(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("related_places")
    val relatedPlaces: RelatedPlaces? = RelatedPlaces(),
    @SerialName("timezone")
    val timezone: String? = ""
)
package dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Result(
    @SerialName("fsq_id")
    val fsqId: String? = null,
    @SerialName("categories")
    val categories: List<Category?>? = null,
    @SerialName("chains")
    val chains: List<Chain?>? = null,
    @SerialName("distance")
    val distance: Int? = null,
    @SerialName("geocodes")
    val geocodes: Geocodes? = null,
    @SerialName("link")
    val link: String? = null,
    @SerialName("location")
    val location: Location? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("related_places")
    val relatedPlaces: RelatedPlaces? = null,
    @SerialName("timezone")
    val timezone: String? = null
)
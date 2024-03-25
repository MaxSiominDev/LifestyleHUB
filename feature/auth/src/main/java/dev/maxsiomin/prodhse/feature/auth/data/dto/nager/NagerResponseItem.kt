package dev.maxsiomin.prodhse.feature.auth.data.dto.nager

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class NagerResponseItem(
    @SerialName("date")
    val date: String? = null,
    @SerialName("localName")
    val localName: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("countryCode")
    val countryCode: String? = null,
    @SerialName("fixed")
    val fixed: Boolean? = null,
    @SerialName("global")
    val global: Boolean? = null,
    @SerialName("types")
    val types: List<String?>? = null
)
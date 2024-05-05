package dev.maxsiomin.prodhse.feature.home.data.dto.place_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
internal data class Hours(
    @SerialName("display")
    val display: String? = null,
    @SerialName("is_local_holiday")
    val isLocalHoliday: Boolean? = null,
    @SerialName("open_now")
    val openNow: Boolean? = null,
    @SerialName("regular")
    val regular: List<Regular?>? = null
)
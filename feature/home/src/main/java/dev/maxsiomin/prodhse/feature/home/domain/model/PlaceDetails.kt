package dev.maxsiomin.prodhse.feature.home.domain.model

import kotlinx.serialization.Serializable

@Serializable
internal data class PlaceDetails(
    val timeUpdated: Long,
    val name: String,
    val address: String,
    val photos: List<Photo>,
    val fsqId: String,
    val categories: String,
    val workingHours: List<String>? = null,
    val isVerified: Boolean,
    val rating: Double? = null,
    val website: String?,
    val isOpenNow: Boolean,
    val phone: String? = null,
    val email: String? = null,
)

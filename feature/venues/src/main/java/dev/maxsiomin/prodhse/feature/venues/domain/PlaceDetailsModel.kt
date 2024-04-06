package dev.maxsiomin.prodhse.feature.venues.domain

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailsModel(
    val timeUpdated: Long,
    val name: String,
    val address: String,
    val photos: List<PhotoModel>,
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

package dev.maxsiomin.prodhse.feature.venues.domain

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailsModel(
    val timeUpdated: Long,
    val name: String,
    val address: String,
    val photos: List<PhotoModel>,
    val id: String,
    val categories: String,
    val workingHours: List<String>?,
    val isVerified: Boolean,
    val rating: Double?,
    val website: String?,
    val isOpenNow: Boolean,
)
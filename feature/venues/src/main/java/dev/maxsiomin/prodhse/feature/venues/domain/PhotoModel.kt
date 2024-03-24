package dev.maxsiomin.prodhse.feature.venues.domain

import kotlinx.serialization.Serializable

@Serializable
data class PhotoModel(
    val id: String,
    val url: String,
)

package dev.maxsiomin.prodhse.feature.home.domain

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: String,
    val url: String,
)

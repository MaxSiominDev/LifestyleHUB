package dev.maxsiomin.prodhse.feature.home.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: String,
    val url: String,
)

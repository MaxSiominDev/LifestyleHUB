package dev.maxsiomin.prodhse.feature.home.domain.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Photo(
    val id: String,
    val url: String,
)

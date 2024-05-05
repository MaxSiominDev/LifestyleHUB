package dev.maxsiomin.prodhse.feature.home.domain.model

internal data class Place(
    val name: String,
    val address: String,
    val photoUrl: String?,
    val fsqId: String,
    val categories: String,
)

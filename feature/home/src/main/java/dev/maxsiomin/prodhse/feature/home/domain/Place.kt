package dev.maxsiomin.prodhse.feature.home.domain

data class Place(
    val name: String,
    val address: String,
    val photoUrl: String?,
    val fsqId: String,
    val categories: String,
)

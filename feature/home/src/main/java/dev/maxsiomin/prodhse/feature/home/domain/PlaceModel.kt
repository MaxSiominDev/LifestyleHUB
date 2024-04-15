package dev.maxsiomin.prodhse.feature.home.domain

data class PlaceModel(
    val name: String,
    val address: String,
    val photoUrl: String?,
    val fsqId: String,
    val categories: String,
)
package dev.maxsiomin.prodhse.feature.venues.domain

import androidx.room.ColumnInfo

data class PlanModel(
    val placeId: String,
    val note: String,
    val date: Long
)

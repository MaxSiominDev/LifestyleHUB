package dev.maxsiomin.prodhse.feature.venues.domain

data class PlanModel(
    val databaseId: Long,
    val placeFsqId: String,
    val noteTitle: String,
    val noteText: String,
    val dateString: String,
    val date: Long,
)

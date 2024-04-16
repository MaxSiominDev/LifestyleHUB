package dev.maxsiomin.prodhse.feature.home.domain

data class Plan(
    val databaseId: Long,
    val placeFsqId: String,
    val noteTitle: String,
    val noteText: String,
    val dateString: String,
    val date: Long,
)

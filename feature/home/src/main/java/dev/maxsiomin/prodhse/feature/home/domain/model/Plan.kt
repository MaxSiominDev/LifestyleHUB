package dev.maxsiomin.prodhse.feature.home.domain.model

import java.time.LocalDate

internal data class Plan(
    val placeFsqId: String,
    val noteTitle: String,
    val noteText: String,
    val dateString: String,
    val date: LocalDate,
    val databaseId: Long?,
)

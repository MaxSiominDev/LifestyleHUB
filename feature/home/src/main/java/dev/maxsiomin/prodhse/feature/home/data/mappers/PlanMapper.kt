package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.common.data.BidirectionalMapper
import dev.maxsiomin.common.util.DateConverters
import dev.maxsiomin.prodhse.core.util.DateFormatter
import dev.maxsiomin.prodhse.feature.home.data.local.PlanEntity
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import javax.inject.Inject

internal class PlanMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) : BidirectionalMapper<PlanEntity, Plan> {

    override fun toDomain(data: PlanEntity): Plan {
        return Plan(
            placeFsqId = data.placeId,
            noteTitle = data.noteTitle,
            noteText = data.noteText,
            date = DateConverters.epochMillisToLocalDate(data.date),
            databaseId = data.id,
            dateString = dateFormatter.formatDate(data.date),
        )
    }

    override fun toData(domain: Plan): PlanEntity {
        return PlanEntity(
            id = domain.databaseId ?: 0,
            placeId = domain.placeFsqId,
            noteTitle = domain.noteTitle,
            noteText = domain.noteText,
            date = DateConverters.localDateToEpochMillis(domain.date),
        )
    }

}
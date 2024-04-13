package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.prodhse.core.util.DateFormatter
import dev.maxsiomin.prodhse.feature.home.data.local.PlanEntity
import dev.maxsiomin.prodhse.feature.home.domain.PlanModel
import javax.inject.Inject

internal class PlanEntityToUiModelMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) : (PlanEntity) -> PlanModel {

    override fun invoke(entity: PlanEntity): PlanModel {
        return PlanModel(
            placeFsqId = entity.placeId,
            noteTitle = entity.noteTitle,
            noteText = entity.noteText,
            date = entity.date,
            databaseId = entity.id,
            dateString = dateFormatter.formatDate(entity.date),
        )
    }
}
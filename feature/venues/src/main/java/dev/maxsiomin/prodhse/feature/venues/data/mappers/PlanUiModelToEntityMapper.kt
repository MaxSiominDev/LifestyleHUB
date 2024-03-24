package dev.maxsiomin.prodhse.feature.venues.data.mappers

import dev.maxsiomin.prodhse.feature.venues.data.local.PlanEntity
import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel

internal class PlanUiModelToEntityMapper : (PlanModel) -> PlanEntity {

    override fun invoke(plan: PlanModel): PlanEntity {
        return PlanEntity(
            id = 0,
            placeId = plan.placeId,
            noteTitle = plan.noteTitle,
            noteText = plan.noteText,
            date = plan.date,
        )
    }

}
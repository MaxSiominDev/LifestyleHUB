package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.prodhse.feature.home.data.local.PlanEntity
import dev.maxsiomin.prodhse.feature.home.domain.PlanModel

internal class PlanUiModelToEntityMapper : (PlanModel) -> PlanEntity {

    override fun invoke(plan: PlanModel): PlanEntity {
        return PlanEntity(
            id = plan.databaseId,
            placeId = plan.placeFsqId,
            noteTitle = plan.noteTitle,
            noteText = plan.noteText,
            date = plan.date,
        )
    }

}
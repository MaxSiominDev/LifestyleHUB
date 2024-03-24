package dev.maxsiomin.prodhse.feature.venues.data.mappers

import dev.maxsiomin.prodhse.feature.venues.data.local.PlanEntity
import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel

class PlanEntityToUiModelMapper : (PlanEntity) -> PlanModel {

    override fun invoke(entity: PlanEntity): PlanModel {
        return PlanModel(
            placeId = entity.placeId,
            note = entity.note,
            date = entity.date
        )
    }
}
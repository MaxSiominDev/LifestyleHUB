package dev.maxsiomin.prodhse.feature.venues.domain.repository

import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel

internal interface PlansRepository {

    suspend fun addPlan(plan: PlanModel)

    suspend fun getPlans(): List<PlanModel>

    suspend fun editPlan(plan: PlanModel)

    suspend fun getPlanById(id: Long): PlanModel?

}
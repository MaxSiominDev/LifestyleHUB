package dev.maxsiomin.prodhse.feature.home.domain.repository

import dev.maxsiomin.prodhse.feature.home.domain.PlanModel

internal interface PlansRepository {

    suspend fun addPlan(plan: PlanModel)

    suspend fun getPlans(): List<PlanModel>

    suspend fun editPlan(plan: PlanModel)

    suspend fun getPlanById(id: Long): PlanModel?

}
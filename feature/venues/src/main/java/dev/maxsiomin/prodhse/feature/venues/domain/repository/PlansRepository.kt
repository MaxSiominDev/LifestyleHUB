package dev.maxsiomin.prodhse.feature.venues.domain.repository

import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel

interface PlansRepository {

    suspend fun addPlan(plan: PlanModel)

    suspend fun getPlans(): List<PlanModel>

}
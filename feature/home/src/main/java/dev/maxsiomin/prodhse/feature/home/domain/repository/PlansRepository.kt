package dev.maxsiomin.prodhse.feature.home.domain.repository

import dev.maxsiomin.common.domain.resource.DatabaseError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.PlanModel

internal interface PlansRepository {

    suspend fun addPlan(plan: PlanModel)

    suspend fun getPlans(): List<PlanModel>

    suspend fun editPlan(plan: PlanModel)

    suspend fun getPlanById(id: Long): Resource<PlanModel, DatabaseError>

}
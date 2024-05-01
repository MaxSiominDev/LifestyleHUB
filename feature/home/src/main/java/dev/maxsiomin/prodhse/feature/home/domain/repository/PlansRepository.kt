package dev.maxsiomin.prodhse.feature.home.domain.repository

import dev.maxsiomin.common.domain.resource.DatabaseError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan

internal interface PlansRepository {

    suspend fun addPlan(plan: Plan)

    suspend fun getPlans(): List<Plan>

    suspend fun editPlan(plan: Plan)

    suspend fun getPlanById(id: Long): Resource<Plan, DatabaseError>

}
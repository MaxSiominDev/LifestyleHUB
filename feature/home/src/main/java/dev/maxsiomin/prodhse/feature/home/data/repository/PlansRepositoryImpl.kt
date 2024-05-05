package dev.maxsiomin.prodhse.feature.home.data.repository

import dev.maxsiomin.common.data.BidirectionalMapper
import dev.maxsiomin.common.domain.resource.DatabaseError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.local.PlanEntity
import dev.maxsiomin.prodhse.feature.home.data.local.PlansDao
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import javax.inject.Inject

internal class PlansRepositoryImpl @Inject constructor(
    private val dao: PlansDao,
    private val planMapper: BidirectionalMapper<PlanEntity, Plan>,
) : PlansRepository {

    override suspend fun addPlan(plan: Plan) {
        val entity = planMapper.toData(plan)
        dao.upsertPlan(entity)
    }

    override suspend fun getPlans(): List<Plan> {
        return dao.getPlans().map {
            planMapper.toDomain(it)
        }
    }

    override suspend fun editPlan(plan: Plan) {
        val entity = planMapper.toData(plan)
        dao.upsertPlan(entity)
    }

    override suspend fun getPlanById(id: Long): Resource<Plan, DatabaseError> {
        val plan = dao.getPlanById(id)?.let(planMapper::toDomain)
        return if (plan != null) {
            Resource.Success(plan)
        } else {
            Resource.Error(DatabaseError.NotFound)
        }
    }

    override suspend fun deletePlan(plan: Plan) {
        dao.deletePlan(planMapper.toData(plan))
    }

}
package dev.maxsiomin.prodhse.feature.home.data.repository

import dev.maxsiomin.common.data.BidirectionalMapper
import dev.maxsiomin.common.domain.resource.LocalError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.local.PlanEntity
import dev.maxsiomin.prodhse.feature.home.data.local.PlansDao
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PlansRepositoryImpl @Inject constructor(
    private val dao: PlansDao,
    private val planMapper: BidirectionalMapper<PlanEntity, Plan>,
) : PlansRepository {

    override suspend fun addPlan(plan: Plan) = withContext(Dispatchers.IO) {
        val entity = planMapper.toData(plan)
        dao.upsertPlan(entity)
    }

    override suspend fun getPlans(): List<Plan> = withContext(Dispatchers.IO) {
        return@withContext dao.getPlans().map {
            planMapper.toDomain(it)
        }
    }

    override suspend fun editPlan(plan: Plan) = withContext(Dispatchers.IO) {
        val entity = planMapper.toData(plan)
        dao.upsertPlan(entity)
    }

    override suspend fun getPlanById(
        id: Long
    ): Resource<Plan, LocalError> = withContext(Dispatchers.IO) {
        val plan = dao.getPlanById(id)?.let(planMapper::toDomain)
        return@withContext if (plan != null) {
            Resource.Success(plan)
        } else {
            Resource.Error(LocalError.Unknown(null))
        }
    }

    override suspend fun deletePlan(plan: Plan) = withContext(Dispatchers.IO) {
        dao.deletePlan(planMapper.toData(plan))
    }

}
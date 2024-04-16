package dev.maxsiomin.prodhse.feature.home.data.repository

import dev.maxsiomin.common.domain.resource.DatabaseError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.data.local.PlansDao
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlanEntityToUiModelMapper
import dev.maxsiomin.prodhse.feature.home.data.mappers.PlanUiModelToEntityMapper
import dev.maxsiomin.prodhse.feature.home.domain.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import javax.inject.Inject

internal class PlansRepositoryImpl @Inject constructor(
    private val dao: PlansDao,
    private val planEntityToUiModelMapper: PlanEntityToUiModelMapper,
) : PlansRepository {

    override suspend fun addPlan(plan: Plan) {
        val mapper = PlanUiModelToEntityMapper()
        dao.insertPlan(mapper.invoke(plan))
    }

    override suspend fun getPlans(): List<Plan> {
        return dao.getPlans().map {
            planEntityToUiModelMapper.invoke(it)
        }
    }

    override suspend fun editPlan(plan: Plan) {
        val mapper = PlanUiModelToEntityMapper()
        val entity = mapper.invoke(plan)
        dao.updatePlan(entity)
    }

    override suspend fun getPlanById(id: Long): Resource<Plan, DatabaseError> {
        val plan = dao.getPlanById(id)?.let(planEntityToUiModelMapper)
        return if (plan != null) {
            Resource.Success(plan)
        } else {
            Resource.Error(DatabaseError.NotFound)
        }
    }
}
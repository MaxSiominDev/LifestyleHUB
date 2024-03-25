package dev.maxsiomin.prodhse.feature.venues.data.repository

import dev.maxsiomin.prodhse.feature.venues.data.local.PlansDao
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlanEntityToUiModelMapper
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlanUiModelToEntityMapper
import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlansRepository
import javax.inject.Inject

internal class PlansRepositoryImpl @Inject constructor(
    private val dao: PlansDao,
    private val planEntityToUiModelMapper: PlanEntityToUiModelMapper,
) : PlansRepository {

    override suspend fun addPlan(plan: PlanModel) {
        val mapper = PlanUiModelToEntityMapper()
        dao.insertPlan(mapper.invoke(plan))
    }

    override suspend fun getPlans(): List<PlanModel> {
        return dao.getPlans().map {
            planEntityToUiModelMapper.invoke(it)
        }
    }

    override suspend fun editPlan(plan: PlanModel) {
        val mapper = PlanUiModelToEntityMapper()
        val entity = mapper.invoke(plan)
        dao.updatePlan(entity)
    }

    override suspend fun getPlanById(id: Long): PlanModel? {
        return dao.getPlanById(id)?.let(planEntityToUiModelMapper)
    }
}
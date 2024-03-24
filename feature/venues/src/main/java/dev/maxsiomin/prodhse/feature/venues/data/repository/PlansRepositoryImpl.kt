package dev.maxsiomin.prodhse.feature.venues.data.repository

import dev.maxsiomin.prodhse.feature.venues.data.local.PlansDao
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlanEntityToUiModelMapper
import dev.maxsiomin.prodhse.feature.venues.data.mappers.PlanUiModelToEntityMapper
import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlansRepository
import javax.inject.Inject

internal class PlansRepositoryImpl @Inject constructor(
    private val dao: PlansDao,
) : PlansRepository {

    override suspend fun addPlan(plan: PlanModel) {
        val mapper = PlanUiModelToEntityMapper()
        dao.insertPlan(mapper.invoke(plan))
    }

    override suspend fun getPlans(): List<PlanModel> {
        val mapper = PlanEntityToUiModelMapper()
        return dao.getPlans().map {
            mapper.invoke(it)
        }
    }

}
package dev.maxsiomin.prodhse.feature.home.domain.use_case.plans

import dev.maxsiomin.common.domain.resource.DatabaseError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetPlanByIdUseCase @Inject constructor(private val plansRepo: PlansRepository) {

    suspend operator fun invoke(
        planId: Long
    ): Resource<Plan, DatabaseError> = withContext(Dispatchers.IO) {
        return@withContext plansRepo.getPlanById(id = planId)
    }

}
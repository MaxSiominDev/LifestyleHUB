package dev.maxsiomin.prodhse.feature.home.domain.use_case.plans

import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DeletePlanUseCase @Inject constructor(private val plansRepo: PlansRepository) {

    suspend operator fun invoke(plan: Plan) = withContext(Dispatchers.IO) {
        plansRepo.deletePlan(plan)
    }

}
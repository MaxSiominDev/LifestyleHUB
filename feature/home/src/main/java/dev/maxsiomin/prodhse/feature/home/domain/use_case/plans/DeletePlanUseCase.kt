package dev.maxsiomin.prodhse.feature.home.domain.use_case.plans

import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DeletePlanUseCase @Inject constructor(
    private val plansRepo: PlansRepository,
    private val dispatchers: DispatcherProvider,
) {

    suspend operator fun invoke(plan: Plan) = withContext(dispatchers.io) {
        plansRepo.deletePlan(plan)
    }

}
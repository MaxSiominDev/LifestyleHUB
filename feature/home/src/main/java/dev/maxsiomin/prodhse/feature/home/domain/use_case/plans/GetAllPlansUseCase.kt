package dev.maxsiomin.prodhse.feature.home.domain.use_case.plans

import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import javax.inject.Inject

internal class GetAllPlansUseCase @Inject constructor(private val plansRepo: PlansRepository) {

    suspend operator fun invoke(): List<Plan> {
        return plansRepo.getPlans()
    }

}
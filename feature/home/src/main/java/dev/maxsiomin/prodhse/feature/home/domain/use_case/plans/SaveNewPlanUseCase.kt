package dev.maxsiomin.prodhse.feature.home.domain.use_case.plans

import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import java.time.LocalDate
import javax.inject.Inject

internal class SaveNewPlanUseCase @Inject constructor(
    private val plansRepo: PlansRepository,
) {

    suspend operator fun invoke(
        placeFsqId: String,
        noteTitle: String,
        noteText: String,
        date: LocalDate,
        dateString: String,
    ) {
        val plan = Plan(
            placeFsqId = placeFsqId,
            noteTitle = noteTitle,
            noteText = noteText,
            date = date,
            // Room will create new record
            databaseId = 0,
            dateString = dateString,
        )
        plansRepo.addPlan(plan)
    }

}
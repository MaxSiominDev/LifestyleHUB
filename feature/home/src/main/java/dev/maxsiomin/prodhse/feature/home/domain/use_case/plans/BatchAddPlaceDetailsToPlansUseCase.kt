package dev.maxsiomin.prodhse.feature.home.domain.use_case.plans

import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.use_case.places.GetPlaceDetailsByIdUseCase
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner.PlannerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class BatchAddPlaceDetailsToPlansUseCase @Inject constructor(
    private val getPlaceDetailsByIdUseCase: GetPlaceDetailsByIdUseCase,
    private val dispatchers: DispatcherProvider,
) {

    suspend operator fun invoke(plans: List<Plan>) = withContext(dispatchers.io) {
        val plannerItems = mutableListOf<PlannerItem>()
        plans.map { currentPlan ->
            async {
                var plannerItem: PlannerItem? = null
                val placeDetailsResource = getPlaceDetailsByIdUseCase(currentPlan.placeFsqId)

                when (placeDetailsResource) {
                    is Resource.Error -> Unit

                    is Resource.Success -> {
                        plannerItem = PlannerItem(placeDetailsResource.data, currentPlan)
                    }
                }

                plannerItem?.let {
                    plannerItems.add(it)
                }
            }
        }.awaitAll()

        val sortedItems = sortItems(unsorted = plannerItems)

        return@withContext sortedItems
    }

    private fun sortItems(unsorted: List<PlannerItem>): List<PlannerItem> {
        val sortedItems = unsorted.sortedWith(
            compareByDescending<PlannerItem> {
                it.plan.date
            }.thenBy {
                it.place.name
            }.thenBy {
                it.plan.noteTitle
            }
        )
        return sortedItems
    }

}
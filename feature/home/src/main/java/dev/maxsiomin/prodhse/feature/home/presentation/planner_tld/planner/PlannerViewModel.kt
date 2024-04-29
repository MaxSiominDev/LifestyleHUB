package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PlannerViewModel @Inject constructor(
    private val plansRepo: PlansRepository,
    private val placesRepo: PlacesRepository,
) : StatefulViewModel<PlannerViewModel.State, PlannerViewModel.Effect, PlannerViewModel.Event>() {

    data class State(
        val items: List<PlannerFeedItem> = emptyList(),
        val isRefreshing: Boolean = false,
    )

    override val _state = MutableStateFlow(State())


    sealed class Effect {
        data class GoToEditPlanScreen(val planId: Long) : Effect()
    }


    sealed class Event {
        data class PlanClicked(val planId: Long) : Event()
        data object Refresh : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.PlanClicked -> onEffect(Effect.GoToEditPlanScreen(event.planId))
            Event.Refresh -> loadPlans()
        }
    }

    init {
        loadPlans()
    }

    private fun loadPlans() {
        _state.update {
            it.copy(isRefreshing = true)
        }
        viewModelScope.launch {
            val feedItems = mutableListOf<PlannerFeedItem>()
            val plans = plansRepo.getPlans()
            plans.map { currentPlan ->
                async {
                    var feedItem: PlannerFeedItem? = null
                    val placeDetailsResource = placesRepo.getPlaceDetails(currentPlan.placeFsqId)

                    when (placeDetailsResource) {
                        is Resource.Error -> Unit

                        is Resource.Success -> {
                            feedItem = PlannerFeedItem.Place(placeDetailsResource.data, currentPlan)
                        }
                    }

                    feedItem?.let {
                        feedItems.add(it)
                    }
                }
            }.awaitAll()

            val sortedItems = feedItems.sortedWith(
                compareByDescending<PlannerFeedItem> {
                    when (it) {
                        is PlannerFeedItem.Place -> it.plan.date
                    }
                }.thenBy {
                    when (it) {
                        is PlannerFeedItem.Place -> it.place.name
                    }
                }.thenBy {
                    when (it) {
                        is PlannerFeedItem.Place -> it.plan.noteTitle
                    }
                }
            )

            _state.update {
                it.copy(items = sortedItems, isRefreshing = false)
            }
        }
    }

}
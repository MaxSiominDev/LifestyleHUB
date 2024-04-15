package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.Resource
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PlannerViewModel @Inject constructor(
    private val plansRepo: PlansRepository,
    private val placesRepo: PlacesRepository,
) : ViewModel() {

    data class State(
        val items: List<PlannerFeedItem> = emptyList(),
        val isRefreshing: Boolean = false,
    )

    var state by mutableStateOf(State())
        private set


    sealed class UiEvent {
        data class GoToEditPlanScreen(val planId: Long) : UiEvent()
    }

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()


    sealed class Event {
        data class PlanClicked(val planId: Long) : Event()
        data object Refresh : Event()
    }

    init {
        loadPlans()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.PlanClicked -> viewModelScope.launch {
                _eventFlow.send(UiEvent.GoToEditPlanScreen(event.planId))
            }

            Event.Refresh -> loadPlans()
        }
    }

    private fun loadPlans() {
        state = state.copy(isRefreshing = true)
        viewModelScope.launch {
            val feedItems = mutableListOf<PlannerFeedItem>()
            val plans = plansRepo.getPlans()
            plans.map { currentPlan ->
                async {
                    var feedItem: PlannerFeedItem? = null
                    val placeModel = placesRepo.getPlaceDetails(currentPlan.placeFsqId)
                    placeModel.collect { resource ->
                        when (resource) {
                            is Resource.Error -> Unit

                            is Resource.Success -> {
                                feedItem = PlannerFeedItem.Place(resource.data, currentPlan)
                            }
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

            state = state.copy(items = sortedItems, isRefreshing = false)
        }
    }

}
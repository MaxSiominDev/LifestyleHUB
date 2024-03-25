package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlansRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class PlannerViewModel @Inject constructor(
    private val plansRepo: PlansRepository,
    private val placesRepo: PlacesRepository,
) : ViewModel() {

    data class State(
        val items: List<PlannerFeedItem> = emptyList(),
    )

    var state by mutableStateOf(State())
        private set


    sealed class UiEvent {
        data class GoToEditPlanScreen(val planId: Long) : UiEvent()
        data object StopPullToRefresh : UiEvent()
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

    @Suppress("DeferredResultUnused")
    private fun loadPlans() {
        viewModelScope.launch {
            val feedItems = mutableListOf<PlannerFeedItem>()
            val plans = plansRepo.getPlans()
            plans.forEach { currentPlan ->
                async {
                    var feedItem: PlannerFeedItem? = null
                    val placeModel = placesRepo.getPlaceDetails(currentPlan.placeFsqId)
                    placeModel.collect { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                Timber.i("Details data is loading...")
                            }

                            is Resource.Error -> {
                                Timber.e(resource.exception)
                            }

                            is Resource.Success -> {
                                feedItem = PlannerFeedItem.Venue(resource.data, currentPlan)
                            }
                        }
                    }
                    feedItem?.let {
                        feedItems.add(it)
                    }

                }
            }
            state = state.copy(items = feedItems)
            _eventFlow.send(UiEvent.StopPullToRefresh)
        }
    }

}
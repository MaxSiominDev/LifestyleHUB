package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PlannerViewModel : ViewModel() {

    data class State(
        val places: List<PlaceDetailsModel> = emptyList(),
        val plans: List<PlanModel> = emptyList(),
    )

    var state by mutableStateOf(State())
        private set


    sealed class UiEvent {
        data class GoToEditPlanScreen(val planId: String) : UiEvent()
    }

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()


    sealed class Event {
        data class PlanClicked(val planId: String) : Event()
    }

    init {
        loadPlans()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.PlanClicked -> viewModelScope.launch {
                _eventFlow.send(UiEvent.GoToEditPlanScreen(event.planId))
            }
        }
    }

    private fun loadPlans() {
        viewModelScope.launch {

        }
    }

}
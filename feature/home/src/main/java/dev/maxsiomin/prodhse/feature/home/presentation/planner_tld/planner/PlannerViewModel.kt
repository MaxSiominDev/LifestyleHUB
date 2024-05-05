package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.BatchAddPlaceDetailsToPlansUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.GetAllPlansUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PlannerViewModel @Inject constructor(
    private val getAllPlansUseCase: GetAllPlansUseCase,
    private val batchAddPlaceDetailsToPlansUseCase: BatchAddPlaceDetailsToPlansUseCase,

) : StatefulViewModel<PlannerViewModel.State, PlannerViewModel.Effect, PlannerViewModel.Event>() {

    data class State(
        val items: List<PlannerItem> = emptyList(),
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
            val plans = getAllPlansUseCase()
            val plannerItems = batchAddPlaceDetailsToPlansUseCase(plans)

            _state.update {
                it.copy(items = plannerItems, isRefreshing = false)
            }
        }
    }

}
package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.BatchAddPlaceDetailsToPlansUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.DeletePlanUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.GetAllPlansUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.SavePlanUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PlannerViewModel @Inject constructor(
    private val getAllPlansUseCase: GetAllPlansUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val savePlanUseCase: SavePlanUseCase,
    private val batchAddPlaceDetailsToPlansUseCase: BatchAddPlaceDetailsToPlansUseCase
) : StatefulViewModel<PlannerViewModel.State, PlannerViewModel.Effect, PlannerViewModel.Event>() {

    private var recentlyDeletedPlan: Plan? = null

    data class State(
        val items: List<PlannerItem> = emptyList(),
        val isRefreshing: Boolean = false,
    )

    override val _state = MutableStateFlow(State())


    sealed class Effect {
        data class GoToEditPlanScreen(val planId: Long) : Effect()
        data class ShowMessage(val message: UiText) : Effect()
        data class ShowUndoSnackbar(val message: UiText) : Effect()
    }


    sealed class Event {
        data class PlanClicked(val plan: Plan) : Event()
        data object Refresh : Event()
        data class DeleteClicked(val plan: Plan) : Event()
        data object Undo : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.PlanClicked -> onPlanClicked(event.plan)
            Event.Refresh -> loadPlans()
            is Event.DeleteClicked -> onDeleteClicked(event.plan)
            Event.Undo -> onUndoClicked()
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

    private fun onPlanClicked(plan: Plan) {
        val id = requireNotNull(plan.databaseId) {
            "Plan ID can be null only while saving new plan to the Database"
        }
        onEffect(Effect.GoToEditPlanScreen(id))
    }

    private fun onDeleteClicked(plan: Plan) {
        viewModelScope.launch {
            deletePlanUseCase(plan)
            recentlyDeletedPlan = plan
            onEffect(
                Effect.ShowUndoSnackbar(
                    message = UiText.StringResource(R.string.plan_deleted),
                )
            )
            loadPlans()
        }
    }

    private fun onUndoClicked() {
        val planToRestore = recentlyDeletedPlan ?: return
        recentlyDeletedPlan = null
        viewModelScope.launch {
            savePlanUseCase(planToRestore)
            loadPlans()
            onEffect(
                Effect.ShowMessage(
                    UiText.StringResource(R.string.plan_restored)
                )
            )
        }
    }

}
package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.common.presentation.SnackbarInfo
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.components.PullToRefreshLazyColumn
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home.HomeViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.edit_plan.EditPlanViewModel
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow

@Composable
internal fun PlannerScreenRoot(
    navController: NavController,
    showSnackbar: SnackbarCallback,
    viewModel: PlannerViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(PlannerViewModel.Event.Refresh)
    }

    CollectFlow(viewModel.effectFlow) { effect ->
        when (effect) {
            is PlannerViewModel.Effect.GoToEditPlanScreen -> {
                navController.navigate(Screen.EditPlanScreen.withArgs(effect.planId.toString()))
            }

            is PlannerViewModel.Effect.ShowMessage -> {
                showSnackbar(
                    SnackbarInfo(effect.message)
                )
            }

            is PlannerViewModel.Effect.ShowUndoSnackbar -> {
                showSnackbar(
                    SnackbarInfo(effect.message, action = UiText.StringResource(R.string.undo)) {
                        viewModel.onEvent(PlannerViewModel.Event.Undo)
                    }
                )
            }
        }
    }

    PlannerScreen(state = state, onEvent = viewModel::onEvent)

}

@Composable
private fun PlannerScreen(state: PlannerViewModel.State, onEvent: (PlannerViewModel.Event) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        PullToRefreshLazyColumn(
            isRefreshing = state.isRefreshing,
            onRefresh = { onEvent(PlannerViewModel.Event.Refresh) }
        ) {
            items(state.items) { plannerItem ->
                PlanCard(
                    placeDetails = plannerItem.place,
                    plan = plannerItem.plan,
                    onDelete = {
                        onEvent(PlannerViewModel.Event.DeleteClicked(plannerItem.plan))
                    },
                    onClick = {
                        onEvent(PlannerViewModel.Event.PlanClicked(plannerItem.plan))
                    },
                )
            }
        }

        if (state.items.isEmpty() && !state.isRefreshing) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.no_plans_saved)
            )
        }

        if (state.isRefreshing) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            )
        }
    }
}
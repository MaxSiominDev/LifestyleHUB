package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.maxsiomin.common.presentation.components.PullToRefreshLazyColumn
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow

@Composable
internal fun PlannerScreen(
    state: PlannerViewModel.State,
    effectFlow: Flow<PlannerViewModel.Effect>,
    onEvent: (PlannerViewModel.Event) -> Unit,
    navController: NavController
) {

    CollectFlow(effectFlow) { effect ->
        when (effect) {
            is PlannerViewModel.Effect.GoToEditPlanScreen -> {
                navController.navigate(Screen.EditPlanScreen.withArgs(effect.planId.toString()))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PullToRefreshLazyColumn(
            isRefreshing = state.isRefreshing,
            onRefresh = { onEvent(PlannerViewModel.Event.Refresh) }
        ) {
            items(state.items) { plannerItem ->
                PlanCard(
                    placeDetails = plannerItem.place,
                    onClick = {
                        onEvent(PlannerViewModel.Event.PlanClicked(plannerItem.plan.databaseId))
                    },
                    plan = plannerItem.plan,
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
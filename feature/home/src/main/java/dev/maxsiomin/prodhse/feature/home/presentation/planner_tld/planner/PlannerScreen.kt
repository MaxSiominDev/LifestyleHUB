package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.maxsiomin.common.presentation.components.PullToRefreshLazyColumn
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow

@Composable
internal fun PlannerScreen(
    state: PlannerViewModel.State,
    eventsFlow: Flow<PlannerViewModel.UiEvent>,
    onEvent: (PlannerViewModel.Event) -> Unit,
    navController: NavController
) {

    CollectFlow(eventsFlow) { event ->
        when (event) {
            is PlannerViewModel.UiEvent.GoToEditPlanScreen -> {
                navController.navigate(Screen.EditPlanScreen.withArgs(event.planId.toString()))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PullToRefreshLazyColumn(
            items = state.items,
            content = { feedItem ->
                when (feedItem) {

                    is PlannerFeedItem.Place -> {
                        PlanCard(
                            placeDetails = feedItem.place,
                            onClick = {
                                onEvent(PlannerViewModel.Event.PlanClicked(feedItem.plan.databaseId))
                            },
                            plan = feedItem.plan
                        )
                    }

                }
            },
            isRefreshing = state.isRefreshing,
            onRefresh = { onEvent(PlannerViewModel.Event.Refresh) }
        )

        if (state.items.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.no_plans_saved)
            )
        }
    }

}
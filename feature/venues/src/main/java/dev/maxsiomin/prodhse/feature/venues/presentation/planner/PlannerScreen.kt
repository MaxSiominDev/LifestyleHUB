package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.maxsiomin.prodhse.core.util.CollectFlow
import dev.maxsiomin.prodhse.feature.venues.R
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlannerScreen(
    state: PlannerViewModel.State,
    eventsFlow: Flow<PlannerViewModel.UiEvent>,
    onEvent: (PlannerViewModel.Event) -> Unit,
    navController: NavController
) {

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            onEvent(PlannerViewModel.Event.Refresh)
        }
    }

    LaunchedEffect(Unit) {
        onEvent(PlannerViewModel.Event.Refresh)
    }

    CollectFlow(eventsFlow) { event ->
        when (event) {
            is PlannerViewModel.UiEvent.GoToEditPlanScreen -> {
                navController.navigate(Screen.EditPlanScreen.withArgs(event.planId.toString()))
            }
            PlannerViewModel.UiEvent.StopPullToRefresh -> pullToRefreshState.endRefresh()
        }
    }

    Box(
        Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.items) { feedItem ->
                when (feedItem) {

                    is PlannerFeedItem.Venue -> {
                        PlanCard(
                            placeDetails = feedItem.place,
                            onClick = {
                                onEvent(PlannerViewModel.Event.PlanClicked(feedItem.plan.databaseId))
                            },
                            plan = feedItem.plan
                        )
                    }

                }
            }
        }

        if (state.items.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.no_plans_saved)
            )
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
        )
    }

}
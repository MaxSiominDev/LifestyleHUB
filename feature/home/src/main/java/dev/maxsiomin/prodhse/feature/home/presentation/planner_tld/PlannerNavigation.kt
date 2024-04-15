package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination

fun NavGraphBuilder.addPlannerNavigation(navController: NavController, showSnackbar: SnackbarCallback) {

    composable(route = TopLevelDestination.PLANNER.route) {
        val viewModel: PlannerViewModel = hiltViewModel()
        PlannerScreen(
            state = viewModel.state,
            eventsFlow = viewModel.eventFlow,
            onEvent = viewModel::onEvent,
            navController = navController,
        )
    }

    composable(
        route = Screen.EditPlanScreen.route,
        arguments = Screen.EditPlanScreen.arguments,
    ) { backStackEntry ->
        val viewModel: EditPlanViewModel = hiltViewModel()
        val planId = backStackEntry.arguments?.getString("planId")!!
        EditPlanScreen(
            state = viewModel.state,
            eventsFlow = viewModel.eventsFlow,
            onEvent = viewModel::onEvent,
            showSnackbar = showSnackbar,
            navController = navController,
            planId = planId.toLong(),
        )
    }

}

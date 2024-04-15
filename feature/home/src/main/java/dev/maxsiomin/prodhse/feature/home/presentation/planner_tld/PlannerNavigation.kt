package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.edit_plan.EditPlanScreen
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.edit_plan.EditPlanViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner.PlannerScreen
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner.PlannerViewModel
import dev.maxsiomin.prodhse.navdestinations.Screen

fun NavGraphBuilder.addPlannerNavigation(
    navController: NavController,
    showSnackbar: SnackbarCallback,
    onTldChanged: (Int) -> Unit
) {

    composable(route = Screen.PlannerScreen.route) {
        LaunchedEffect(Unit) {
            onTldChanged(1)
        }
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

package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.edit_plan.EditPlanScreenRoot
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.edit_plan.EditPlanViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner.PlannerScreenRoot
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner.PlannerViewModel
import dev.maxsiomin.prodhse.navdestinations.Screen

fun NavGraphBuilder.addPlannerNavigation(
    showSnackbar: SnackbarCallback,
    onOpened: () -> Unit
) {

    composable(route = Screen.Tld.Planner.route) {
        LaunchedEffect(Unit) {
            onOpened()
        }
        val navController = rememberNavController()
        PlannerNavHost(navController = navController, showSnackbar = showSnackbar)
    }

}

@Composable
private fun PlannerNavHost(navController: NavHostController, showSnackbar: SnackbarCallback) {

    NavHost(navController = navController, startDestination = Screen.PlannerScreen.route) {

        composable(route = Screen.PlannerScreen.route) {
            val viewModel: PlannerViewModel = hiltViewModel()
            PlannerScreenRoot(
                viewModel = viewModel,
                navController = navController,
                showSnackbar = showSnackbar,
            )
        }

        composable(
            route = Screen.EditPlanScreen.route,
            arguments = Screen.EditPlanScreen.arguments,
        ) {
            val viewModel: EditPlanViewModel = hiltViewModel()
            EditPlanScreenRoot(
                viewModel = viewModel,
                showSnackbar = showSnackbar,
                navController = navController,
            )
        }

    }

}

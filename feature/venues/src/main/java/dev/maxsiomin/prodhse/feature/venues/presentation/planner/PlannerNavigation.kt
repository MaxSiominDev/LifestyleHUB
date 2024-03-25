package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.maxsiomin.prodhse.core.util.SnackbarCallback
import dev.maxsiomin.prodhse.feature.venues.presentation.home.AddPlanScreen
import dev.maxsiomin.prodhse.feature.venues.presentation.home.AddPlanViewModel
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination

fun NavGraphBuilder.addPlannerNavigation(navController: NavController, showSnackbar: SnackbarCallback) {

    composable(route = TopLevelDestination.PLANNER.route) {
        val viewModel: PlannerViewModel = hiltViewModel()
        PlannerScreen()
    }

}

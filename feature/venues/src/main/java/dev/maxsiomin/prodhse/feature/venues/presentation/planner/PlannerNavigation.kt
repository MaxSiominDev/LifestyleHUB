package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination

fun NavGraphBuilder.addPlannerNavigation(navController: NavController) {

    composable(route = TopLevelDestination.PLANNER.route) {
        PlannerScreen()
    }

    composable(
        route = Screen.AddPlanScreen.route,
        arguments = Screen.AddPlanScreen.arguments,
    ) { backStackEntry ->
        val fsqId = backStackEntry.arguments?.getString("fsqId")!!
        AddPlanScreen(fsqId = fsqId)
    }


}

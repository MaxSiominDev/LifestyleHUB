package dev.maxsiomin.prodhse.planner.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination

fun NavGraphBuilder.addPlannerNavigation(navController: NavController) {

    composable(TopLevelDestination.PLANNER.route) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Yellow)
        ) {

        }
    }

}
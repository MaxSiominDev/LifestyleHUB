package dev.maxsiomin.prodhse.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.prodhse.ProdhseAppState
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.feature.auth.presentation.addAuthNavigation
import dev.maxsiomin.prodhse.feature.venues.presentation.addVenuesNavigation
import dev.maxsiomin.prodhse.planner.presentation.addPlannerNavigation

@Composable
fun ProdhseNavHost(appState: ProdhseAppState, showSnackbar: SnackbarCallback) {

    val navController = appState.navController
    NavHost(navController = navController, startDestination = TopLevelDestination.HOME.route) {

        addVenuesNavigation(navController = navController, showSnackbar = showSnackbar)

        addPlannerNavigation(navController = navController)

        addAuthNavigation(navController = navController)

    }

}
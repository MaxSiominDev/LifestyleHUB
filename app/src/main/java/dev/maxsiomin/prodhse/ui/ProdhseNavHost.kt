package dev.maxsiomin.prodhse.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.ProdhseAppState
import dev.maxsiomin.prodhse.feature.auth.presentation.addAuthNavigation
import dev.maxsiomin.prodhse.feature.home.presentation.home.addHomeNavigation
import dev.maxsiomin.prodhse.feature.home.presentation.planner.addPlannerNavigation
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination

@Composable
fun ProdhseNavHost(appState: ProdhseAppState, showSnackbar: SnackbarCallback) {

    val navController = appState.navController
    NavHost(navController = navController, startDestination = TopLevelDestination.HOME.route) {

        addHomeNavigation(navController = navController, showSnackbar = showSnackbar)

        addPlannerNavigation(navController = navController, showSnackbar = showSnackbar)

        addAuthNavigation(navController = navController, showSnackbar = showSnackbar)

    }

}
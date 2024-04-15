package dev.maxsiomin.prodhse.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.ProdhseAppState
import dev.maxsiomin.prodhse.feature.auth.presentation.addAuthNavigation
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.addHomeNavigation
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.addPlannerNavigation
import dev.maxsiomin.prodhse.navdestinations.Screen

@Composable
fun ProdhseNavHost(appState: ProdhseAppState, showSnackbar: SnackbarCallback, onTldChanged: (Int) -> Unit) {

    val navController = appState.navController
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        addHomeNavigation(navController = navController, showSnackbar = showSnackbar, onTldChanged = onTldChanged)

        addPlannerNavigation(navController = navController, showSnackbar = showSnackbar, onTldChanged = onTldChanged)

        addAuthNavigation(navController = navController, showSnackbar = showSnackbar, onTldChanged = onTldChanged)

    }

}
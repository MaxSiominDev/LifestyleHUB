package dev.maxsiomin.prodhse.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.ProdhseAppState
import dev.maxsiomin.prodhse.feature.auth.presentation.addProfileNavigation
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.addHomeNavigation
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.addPlannerNavigation
import dev.maxsiomin.prodhse.navdestinations.Screen

@Composable
fun ProdhseNavHost(appState: ProdhseAppState, showSnackbar: SnackbarCallback, onTldChanged: (Int) -> Unit) {

    val rootNavController = appState.navController
    NavHost(navController = rootNavController, startDestination = Screen.Tld.Home.route) {

        addHomeNavigation(showSnackbar = showSnackbar, onOpened = { onTldChanged(0) })

        addPlannerNavigation(showSnackbar = showSnackbar, onOpened = { onTldChanged(1) })

        addProfileNavigation(showSnackbar = showSnackbar, onOpened = { onTldChanged(2) } )

    }

}
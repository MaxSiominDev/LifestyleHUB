package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.navdestinations.Screen

fun NavGraphBuilder.addVenuesNavigation(navController: NavController, showSnackbar: SnackbarCallback) {

    navigation(route = Screen.VenuesGraph.route, startDestination = Screen.VenuesScreen.route) {
        composable(route = Screen.VenuesScreen.route) {
            VenuesScreen(showSnackbar)
        }
    }

}
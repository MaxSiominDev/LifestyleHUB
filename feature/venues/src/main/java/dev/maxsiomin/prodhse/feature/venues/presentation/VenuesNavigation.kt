package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination

fun NavGraphBuilder.addVenuesNavigation(navController: NavController, showSnackbar: SnackbarCallback) {

    composable(route = TopLevelDestination.HOME.route) {
        VenuesScreen(navController, showSnackbar)
    }

    composable(route = "hello_screen") {
        Column(Modifier.fillMaxSize().background(Color.Magenta)) {

        }
    }

}
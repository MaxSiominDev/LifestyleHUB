package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination

fun NavGraphBuilder.addVenuesNavigation(
    navController: NavController,
    showSnackbar: SnackbarCallback
) {

    composable(route = TopLevelDestination.HOME.route) {
        val viewModel: VenuesViewModel = hiltViewModel()
        VenuesScreen(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            navController = navController,
            showSnackbar = showSnackbar,
            eventsFlow = viewModel.eventsFlow,
        )
    }

    composable(route = Screen.DetailsScreen.route) {
        val viewModel: DetailsViewModel = hiltViewModel()
        DetailsScreen(

        )
    }

}
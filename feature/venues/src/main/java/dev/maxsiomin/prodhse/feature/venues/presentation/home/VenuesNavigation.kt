package dev.maxsiomin.prodhse.feature.venues.presentation.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.maxsiomin.prodhse.core.util.SnackbarCallback
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination
import java.net.URLDecoder

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

    composable(
        route = Screen.DetailsScreen.route,
        arguments = Screen.DetailsScreen.arguments,
    ) { backStackEntry ->
        val viewModel: DetailsViewModel = hiltViewModel()
        val fsqId = backStackEntry.arguments!!.getString("fsqId")!!
        DetailsScreen(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            fsqId = fsqId,
            eventsFlow = viewModel.eventsFlow,
            navController = navController,
        )
    }

    composable(
        route = Screen.PhotoScreen.route,
        arguments = Screen.PhotoScreen.arguments,
    ) { backStackEntry ->
        val encodedUrl = backStackEntry.arguments!!.getString("url")!!
        val url = URLDecoder.decode(encodedUrl, "UTF-8")
        PhotoScreen(url = url)
    }

}
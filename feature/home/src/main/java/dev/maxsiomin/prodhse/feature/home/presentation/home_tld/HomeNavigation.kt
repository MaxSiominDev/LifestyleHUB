package dev.maxsiomin.prodhse.feature.home.presentation.home_tld

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.add_plan.AddPlanScreen
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.add_plan.AddPlanViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.details.DetailsScreen
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.details.DetailsViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.fullscreen_photo.BrowsePhotoScreen
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.fullscreen_photo.BrowsePhotoViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home.HomeScreen
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home.HomeViewModel
import dev.maxsiomin.prodhse.navdestinations.Screen
import java.net.URLDecoder

fun NavGraphBuilder.addHomeNavigation(
    navController: NavController,
    showSnackbar: SnackbarCallback,
    onTldChanged: (Int) -> Unit,
) {

    composable(route = Screen.HomeScreen.route) {
        LaunchedEffect(Unit) {
            onTldChanged(0)
        }
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(
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
    ) {
        val viewModel: DetailsViewModel = hiltViewModel()
        DetailsScreen(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            eventsFlow = viewModel.eventsFlow,
            navController = navController,
            showSnackbar = showSnackbar,
        )
    }

    composable(
        route = Screen.BrowsePhotoScreen.route,
        arguments = Screen.BrowsePhotoScreen.arguments,
    ) {
        val viewModel: BrowsePhotoViewModel = hiltViewModel()
        BrowsePhotoScreen(viewModel.state)
    }

    composable(
        route = Screen.AddPlanScreen.route,
        arguments = Screen.AddPlanScreen.arguments,
    ) {
        val viewModel: AddPlanViewModel = hiltViewModel()
        AddPlanScreen(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            navController = navController,
            snackbarCallback = showSnackbar,
            eventFlow = viewModel.eventsFlow,
        )
    }


}
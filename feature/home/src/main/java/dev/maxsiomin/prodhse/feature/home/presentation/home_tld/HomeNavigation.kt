package dev.maxsiomin.prodhse.feature.home.presentation.home_tld

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

fun NavGraphBuilder.addHomeNavigation(
    showSnackbar: SnackbarCallback,
    onOpened: () -> Unit,
) {

    composable(Screen.Tld.Home.route) {
        LaunchedEffect(Unit) {
            onOpened()
        }
        val navController = rememberNavController()
        HomeNavHost(navController = navController, showSnackbar = showSnackbar)
    }

}

@Composable
fun HomeNavHost(navController: NavHostController, showSnackbar: SnackbarCallback) {

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            HomeScreen(
                state = state,
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

}

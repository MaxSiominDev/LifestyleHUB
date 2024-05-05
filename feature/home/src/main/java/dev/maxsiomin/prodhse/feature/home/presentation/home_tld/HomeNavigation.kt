package dev.maxsiomin.prodhse.feature.home.presentation.home_tld

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.add_plan.AddPlanScreenRoot
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.add_plan.AddPlanViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.details.DetailsScreenRoot
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.details.DetailsViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.browse_photo.BrowsePhotoScreenRoot
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.browse_photo.BrowsePhotoViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home.HomeScreenRoot
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
private fun HomeNavHost(navController: NavHostController, showSnackbar: SnackbarCallback) {

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreenRoot(
                viewModel = viewModel,
                navController = navController,
                showSnackbar = showSnackbar,
            )
        }

        composable(
            route = Screen.DetailsScreen.route,
            arguments = Screen.DetailsScreen.arguments,
        ) {
            val viewModel: DetailsViewModel = hiltViewModel()
            DetailsScreenRoot(
                viewModel = viewModel,
                navController = navController,
                showSnackbar = showSnackbar,
            )
        }

        composable(
            route = Screen.BrowsePhotoScreen.route,
            arguments = Screen.BrowsePhotoScreen.arguments,
        ) {
            val viewModel: BrowsePhotoViewModel = hiltViewModel()
            BrowsePhotoScreenRoot(viewModel = viewModel)
        }

        composable(
            route = Screen.AddPlanScreen.route,
            arguments = Screen.AddPlanScreen.arguments,
        ) {
            val viewModel: AddPlanViewModel = hiltViewModel()
            AddPlanScreenRoot(
                navController = navController,
                snackbarCallback = showSnackbar,
                viewModel = viewModel,
            )
        }

    }

}

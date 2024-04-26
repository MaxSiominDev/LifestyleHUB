package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.navdestinations.Screen

fun NavGraphBuilder.addProfileNavigation(
    showSnackbar: SnackbarCallback,
    onTldChanged: (Int) -> Unit,
) {

    composable(route = Screen.Tld.Profile.route) {
        LaunchedEffect(Unit) {
            onTldChanged(2)
        }
        val navController = rememberNavController()
        ProfileNavHost(navController = navController, showSnackbar = showSnackbar)
    }

}

@Composable
fun ProfileNavHost(navController: NavHostController, showSnackbar: SnackbarCallback) {

    NavHost(navController = navController, startDestination = Screen.ProfileScreen.route) {

        composable(route = Screen.ProfileScreen.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }

        composable(route = Screen.AuthScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            AuthScreen(
                eventsFlow = viewModel.eventsFlow,
                onEvent = viewModel::onEvent,
                navController = navController,
            )
        }

        composable(route = Screen.LoginScreen.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                state = viewModel.state,
                eventsFlow = viewModel.eventsFlow,
                onEvent = viewModel::onEvent,
                showSnackbar = showSnackbar,
                navController = navController,
            )
        }

        composable(route = Screen.SignupScreen.route) {
            val viewModel: SignupViewModel = hiltViewModel()
            SignupScreen(
                state = viewModel.state,
                eventsFlow = viewModel.eventsFlow,
                onEvent = viewModel::onEvent,
                navController = navController,
                showSnackbar = showSnackbar,
            )
        }

        composable(route = Screen.SuccessfulRegistrationScreen.route) {
            val viewModel: SuccessfulRegistrationViewModel = hiltViewModel()
            SuccessfulRegistrationScreen(
                navController = navController,
                eventsFlow = viewModel.eventsFlow,
                onEvent = viewModel::onEvent
            )
        }

    }

}

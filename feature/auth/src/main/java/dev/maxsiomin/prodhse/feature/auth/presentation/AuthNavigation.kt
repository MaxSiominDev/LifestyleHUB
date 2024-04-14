package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination

fun NavGraphBuilder.addAuthNavigation(
    navController: NavController,
    showSnackbar: SnackbarCallback,
) {

    composable(route = TopLevelDestination.PROFILE.route) {
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
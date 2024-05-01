package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.prodhse.feature.auth.presentation.auth.AuthScreen
import dev.maxsiomin.prodhse.feature.auth.presentation.auth.AuthViewModel
import dev.maxsiomin.prodhse.feature.auth.presentation.login.LoginScreen
import dev.maxsiomin.prodhse.feature.auth.presentation.login.LoginViewModel
import dev.maxsiomin.prodhse.feature.auth.presentation.profile.ProfileScreen
import dev.maxsiomin.prodhse.feature.auth.presentation.profile.ProfileViewModel
import dev.maxsiomin.prodhse.feature.auth.presentation.signup.SignupScreen
import dev.maxsiomin.prodhse.feature.auth.presentation.signup.SignupViewModel
import dev.maxsiomin.prodhse.feature.auth.presentation.successful_registration.SuccessfulRegistrationScreen
import dev.maxsiomin.prodhse.feature.auth.presentation.successful_registration.SuccessfulRegistrationViewModel
import dev.maxsiomin.prodhse.navdestinations.Screen

fun NavGraphBuilder.addProfileNavigation(
    showSnackbar: SnackbarCallback,
    onOpened: () -> Unit,
) {

    composable(route = Screen.Tld.Profile.route) {
        LaunchedEffect(Unit) {
            onOpened()
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
            val state by viewModel.state.collectAsStateWithLifecycle()
            ProfileScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController,
            )
        }

        composable(route = Screen.AuthScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            AuthScreen(
                effectFlow = viewModel.effectFlow,
                onEvent = viewModel::onEvent,
                navController = navController,
            )
        }

        composable(route = Screen.LoginScreen.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LoginScreen(
                state = state,
                effectFlow = viewModel.effectFlow,
                onEvent = viewModel::onEvent,
                showSnackbar = showSnackbar,
                navController = navController,
            )
        }

        composable(route = Screen.SignupScreen.route) {
            val viewModel: SignupViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SignupScreen(
                state = state,
                effectFlow = viewModel.effectFlow,
                onEvent = viewModel::onEvent,
                navController = navController,
                showSnackbar = showSnackbar,
            )
        }

        composable(route = Screen.SuccessfulRegistrationScreen.route) {
            val viewModel: SuccessfulRegistrationViewModel = hiltViewModel()
            SuccessfulRegistrationScreen(
                navController = navController,
                effectFlow = viewModel.effectFlow,
                onEvent = viewModel::onEvent
            )
        }

    }

}

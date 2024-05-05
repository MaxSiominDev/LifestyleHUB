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
import dev.maxsiomin.prodhse.feature.auth.presentation.auth.AuthScreenRoot
import dev.maxsiomin.prodhse.feature.auth.presentation.auth.AuthViewModel
import dev.maxsiomin.prodhse.feature.auth.presentation.login.LoginScreenRoot
import dev.maxsiomin.prodhse.feature.auth.presentation.login.LoginViewModel
import dev.maxsiomin.prodhse.feature.auth.presentation.profile.ProfileScreenRoot
import dev.maxsiomin.prodhse.feature.auth.presentation.profile.ProfileViewModel
import dev.maxsiomin.prodhse.feature.auth.presentation.signup.SignupScreenRoot
import dev.maxsiomin.prodhse.feature.auth.presentation.signup.SignupViewModel
import dev.maxsiomin.prodhse.feature.auth.presentation.successful_registration.SuccessfulRegistrationScreenRoot
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
private fun ProfileNavHost(navController: NavHostController, showSnackbar: SnackbarCallback) {

    NavHost(navController = navController, startDestination = Screen.ProfileScreen.route) {

        composable(route = Screen.ProfileScreen.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreenRoot(
                viewModel = viewModel,
                navController = navController,
            )
        }

        composable(route = Screen.AuthScreen.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            AuthScreenRoot(
                viewModel = viewModel,
                navController = navController,
            )
        }

        composable(route = Screen.LoginScreen.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreenRoot(
                viewModel = viewModel,
                showSnackbar = showSnackbar,
                navController = navController,
            )
        }

        composable(route = Screen.SignupScreen.route) {
            val viewModel: SignupViewModel = hiltViewModel()
            SignupScreenRoot(
                viewModel = viewModel,
                navController = navController,
                showSnackbar = showSnackbar,
            )
        }

        composable(route = Screen.SuccessfulRegistrationScreen.route) {
            val viewModel: SuccessfulRegistrationViewModel = hiltViewModel()
            SuccessfulRegistrationScreenRoot(
                navController = navController,
                viewModel = viewModel,
            )
        }

    }

}

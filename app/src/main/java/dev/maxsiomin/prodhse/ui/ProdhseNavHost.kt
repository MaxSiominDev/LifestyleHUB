package dev.maxsiomin.prodhse.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.prodhse.MainViewModel
import dev.maxsiomin.prodhse.navdestinations.Screen

@Composable
fun ProdhseNavHost(authManager: AuthManager) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.TabsScreen.route) {
        composable(Screen.TabsScreen.route) {
            val viewModel: MainViewModel = viewModel()
            TabsScreen(viewModel.state, viewModel::onEvent)
        }
    }
}
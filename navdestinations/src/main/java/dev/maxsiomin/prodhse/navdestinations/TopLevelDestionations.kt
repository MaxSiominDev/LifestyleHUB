package dev.maxsiomin.prodhse.navdestinations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens. Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val titleTextId: Int,
    val route: String,
    val allScreens: List<String>,
) {
    HOME(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        titleTextId = R.string.home,
        route = Screen.HomeScreen.route,
        allScreens = listOf(
            Screen.HomeScreen.route,
            Screen.DetailsScreen.route,
            Screen.AddPlanScreen.route,
        ),
    ),
    PLANNER(
        selectedIcon = Icons.Filled.Event,
        unselectedIcon = Icons.Outlined.Event,
        titleTextId = R.string.plans,
        route = Screen.PlannerScreen.route,
        allScreens = listOf(
            Screen.PlannerScreen.route,
            Screen.EditPlanScreen.route,
        ),
    ),
    PROFILE(
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        titleTextId = R.string.profile,
        route = Screen.ProfileScreen.route,
        allScreens = listOf(
            Screen.ProfileScreen.route,
            Screen.AuthScreen.route,
            Screen.LoginScreen.route,
            Screen.SignupScreen.route,
            Screen.SuccessfulRegistrationScreen.route,
        )
    ),
}
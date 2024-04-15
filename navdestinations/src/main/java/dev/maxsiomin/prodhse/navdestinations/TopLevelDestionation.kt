package dev.maxsiomin.prodhse.navdestinations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val titleTextId: Int,
    val route: String,
)

val topLevelDestinations = listOf(
    TopLevelDestination(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        titleTextId = R.string.home,
        route = Screen.HomeScreen.route,
    ),
    TopLevelDestination(
        selectedIcon = Icons.Filled.Event,
        unselectedIcon = Icons.Outlined.Event,
        titleTextId = R.string.plans,
        route = Screen.PlannerScreen.route,
    ),
    TopLevelDestination(
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        titleTextId = R.string.profile,
        route = Screen.ProfileScreen.route,
    )
)

package dev.maxsiomin.prodhse.navdestinations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import dev.maxsiomin.common.presentation.UiText

data class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: UiText,
    val route: String,
)

val topLevelDestinations = listOf(

    TopLevelDestination(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        title = UiText.StringResource(R.string.home),
        route = Screen.Tld.Home.route,
    ),

    TopLevelDestination(
        selectedIcon = Icons.Filled.Event,
        unselectedIcon = Icons.Outlined.Event,
        title = UiText.StringResource(R.string.plans),
        route = Screen.Tld.Planner.route,
    ),

    TopLevelDestination(
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        title = UiText.StringResource(R.string.profile),
        route = Screen.Tld.Profile.route,
    )

)

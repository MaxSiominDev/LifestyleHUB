package dev.maxsiomin.prodhse.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.prodhse.MainViewModel
import dev.maxsiomin.prodhse.R
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.core.SnackbarInfo
import dev.maxsiomin.prodhse.feature.venues.presentation.addVenuesNavigation
import dev.maxsiomin.prodhse.navdestinations.Screen

@Composable
fun TabsScreen(state: MainViewModel.State, showSnackbar: SnackbarCallback, onEvent: (MainViewModel.Event) -> Unit) {

    val tabsNavController = rememberNavController()

    val items = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.VenuesGraph.route,
        ),
        BottomNavigationItem(
            title =  stringResource(id = R.string.plans),
            selectedIcon = Icons.Filled.Event,
            unselectedIcon = Icons.Outlined.Event,
            route = Screen.PlannerGraph.route,
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.profile),
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            route = Screen.AuthGraph.route,
        ),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.fastForEachIndexed { index, item ->
                    val isSelected = index == state.selectedBottomNavBarItemIndex
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            onEvent(MainViewModel.Event.BottomNavBarDestinationChanged(index))
                            tabsNavController.navigate(item.route) {
                                // Clear back stack
                                popUpTo(0) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when revisiting a top-level destination
                                restoreState = true
                            }
                        },
                        icon = {
                            val vector = if (isSelected) item.selectedIcon else item.unselectedIcon
                            Icon(imageVector = vector, contentDescription = item.title)
                        })
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = tabsNavController,
            startDestination = Screen.VenuesGraph.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            addVenuesNavigation(navController = tabsNavController, showSnackbar = showSnackbar)

            navigation(
                route = Screen.PlannerGraph.route,
                startDestination = Screen.PlannerScreen.route
            ) {
                composable(Screen.PlannerScreen.route) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Yellow)
                    ) {

                    }
                }
            }

            navigation(route = Screen.AuthGraph.route, startDestination = Screen.AuthScreen.route) {
                composable(Screen.AuthScreen.route) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Blue)
                    ) {

                    }
                }
            }
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
)


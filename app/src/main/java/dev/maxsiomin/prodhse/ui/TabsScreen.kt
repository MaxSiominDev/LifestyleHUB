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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.prodhse.R
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.feature.auth.presentation.addAuthNavigation
import dev.maxsiomin.prodhse.feature.venues.presentation.addVenuesNavigation
import dev.maxsiomin.prodhse.navdestinations.Screen
import dev.maxsiomin.prodhse.planner.presentation.addPlannerNavigation

/*@Composable
fun TabsScreen(
    showSnackbar: SnackbarCallback
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by tabsNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.parent?.route
                items.fastForEachIndexed { index, item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            tabsNavController.navigate(item.route) {
                                popUpTo(tabsNavController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            val vector = if (isSelected) item.selectedIcon else item.unselectedIcon
                            Icon(imageVector = vector, contentDescription = item.title)
                        },
                        label = { Text(text = item.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = tabsNavController,
            startDestination = Screen.VenuesScreen.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            addVenuesNavigation(navController = tabsNavController, showSnackbar = showSnackbar)

            addPlannerNavigation(navController = tabsNavController)

            addAuthNavigation(navController = tabsNavController)
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
)
*/

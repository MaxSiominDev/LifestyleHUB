package dev.maxsiomin.prodhse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination
import dev.maxsiomin.prodhse.navdestinations.topLevelDestinations

@Composable
fun rememberProdhseAppState(
    navController: NavHostController = rememberNavController(),
    topLevelDestinations: List<TopLevelDestination> = dev.maxsiomin.prodhse.navdestinations.topLevelDestinations,
): ProdhseAppState {
    return remember(navController) {
        ProdhseAppState(
            navController = navController,
            topLevelDestinations = topLevelDestinations,
        )
    }
}

@Stable
class ProdhseAppState(
    val navController: NavHostController,
    val topLevelDestinations: List<TopLevelDestination>,
) {

    fun navigateToTopLevelDestination(
        topLevelDestination: TopLevelDestination,
        currentTopLevelDestination: TopLevelDestination,
    ) {
        /** Restore TLD state to initial if users clicks TLD being on this TLD
         * Less abstract example: if user is at login screen (auth TLD),
         * after pressing Auth icon on BottomNavBar,
         * app will open auth screen (initial auth TLD destination)
         */
        if (topLevelDestination == currentTopLevelDestination) {
            navController.popBackStack(
                currentTopLevelDestination.route,
                inclusive = false,
                saveState = false,
            )
            return
        }

        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        navController.navigate(
            topLevelDestination.route,
            topLevelNavOptions,
        )

    }

}

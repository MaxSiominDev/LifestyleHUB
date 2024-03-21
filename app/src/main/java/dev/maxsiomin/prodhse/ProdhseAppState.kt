package dev.maxsiomin.prodhse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberProdhseAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): ProdhseAppState {
    return remember(
        navController,
        coroutineScope,
    ) {
        ProdhseAppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class ProdhseAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            TopLevelDestination.HOME.route -> TopLevelDestination.HOME
            TopLevelDestination.PLANNER.route -> TopLevelDestination.PLANNER
            TopLevelDestination.AUTH.route -> TopLevelDestination.AUTH
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
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

            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigate(
                    TopLevelDestination.HOME.route,
                    topLevelNavOptions
                )

                TopLevelDestination.PLANNER -> navController.navigate(
                    TopLevelDestination.PLANNER.route,
                    topLevelNavOptions
                )

                TopLevelDestination.AUTH -> navController.navigate(
                    TopLevelDestination.AUTH.route,
                    topLevelNavOptions
                )
            }
        }
    }

}
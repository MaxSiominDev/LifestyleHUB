package dev.maxsiomin.prodhse.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.prodhse.ProdhseAppState
import dev.maxsiomin.prodhse.core.SnackbarInfo
import dev.maxsiomin.prodhse.navdestinations.TopLevelDestination
import kotlinx.coroutines.launch

@Composable
fun ProdhseApp(appState: ProdhseAppState) {

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val showSnackbar = remember {
        { info: SnackbarInfo ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = info.message.asString(context),
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
            }
            Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar {

                appState.topLevelDestinations.forEach { destination ->
                    val currentDestination = appState.currentTopLevelDestination
                    val isSelected = currentDestination == destination
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            appState.navigateToTopLevelDestination(
                                topLevelDestination = destination,
                                currentTopLevelDestination = currentDestination,
                            )
                        },
                        icon = {
                            val vector =
                                if (isSelected) destination.selectedIcon else destination.unselectedIcon
                            Icon(
                                imageVector = vector,
                                contentDescription = stringResource(id = destination.titleTextId)
                            )
                        },
                        label = { Text(text = stringResource(id = destination.titleTextId)) }
                    )
                }

            }
        }
        /*topBar = {
            TopAppBar(
                title = { Text(text = "LifestyleHUB") },
                navigationIcon = {
                    Icon(painter = , contentDescription = )
                }

            )
        }*/
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ProdhseNavHost(appState = appState, showSnackbar = showSnackbar)
        }
    }

}

/*private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false*/

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    val hierarchy = this?.hierarchy?.toList() ?: return false
    val result = hierarchy.any {
        val route = it.route ?: return@any false
        route.contains(destination.route, true)
    }
    return result
}


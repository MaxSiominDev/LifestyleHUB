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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.maxsiomin.common.presentation.SnackbarInfo
import dev.maxsiomin.prodhse.ProdhseAppState
import dev.maxsiomin.prodhse.R
import dev.maxsiomin.prodhse.navdestinations.topLevelDestinations
import kotlinx.coroutines.launch

@Composable
fun ProdhseApp(appState: ProdhseAppState) {

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val showSnackbar: (SnackbarInfo) -> Unit = remember {
        { info: SnackbarInfo ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = info.message.asString(context),
                    actionLabel = info.action.asString(context),
                    duration = SnackbarDuration.Short,
                ).also { result ->
                    info.onResult?.invoke(result)
                }
            }
        }
    }

    var selectedBottomNavBarIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar {
                topLevelDestinations.forEachIndexed { index, destination ->
                    val isSelected = selectedBottomNavBarIndex == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            appState.navigateToTopLevelDestination(
                                topLevelDestination = destination,
                                currentTopLevelDestination = topLevelDestinations[selectedBottomNavBarIndex],
                            )
                            selectedBottomNavBarIndex = index
                        },
                        icon = {
                            val vector =
                                if (isSelected) destination.selectedIcon else destination.unselectedIcon
                            Icon(
                                imageVector = vector,
                                contentDescription = destination.title.asString(),
                            )
                        },
                        label = { Text(text = destination.title.asString()) }
                    )
                }

            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            ProdhseNavHost(
                appState = appState,
                showSnackbar = showSnackbar,
                onTldChanged = { selectedBottomNavBarIndex = it },
            )
        }
    }

}
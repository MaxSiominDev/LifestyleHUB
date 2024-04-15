package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.maxsiomin.common.extensions.openAppSettings
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.common.presentation.SnackbarInfo
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.components.PullToRefreshLazyColumn
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.core.location.PermissionChecker
import dev.maxsiomin.prodhse.core.ui.LocationPermissionTextProvider
import dev.maxsiomin.prodhse.core.ui.PermissionDialog
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Composable
internal fun HomeScreen(
    state: HomeViewModel.State,
    onEvent: (HomeViewModel.Event) -> Unit,
    eventsFlow: Flow<HomeViewModel.UiEvent>,
    navController: NavController,
    showSnackbar: SnackbarCallback,
) {

    val activity = LocalContext.current as ComponentActivity

    /** I ask both coarse and fine location permissions, but if user grants only coarse,
     * I do nothing about that
     */
    val permissions = arrayOf(
        PermissionChecker.COARSE_LOCATION_PERMISSION,
        PermissionChecker.FINE_LOCATION_PERMISSION,
    )

    val locationPermissionResultLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { results ->
                onEvent(
                    HomeViewModel.Event.LocationPermissionResult(
                        coarseIsGranted = results[PermissionChecker.COARSE_LOCATION_PERMISSION]!!,
                    )
                )
            }
        )

    CollectFlow(eventsFlow) { event ->
        when (event) {
            is HomeViewModel.UiEvent.ShowError -> {
                showSnackbar(SnackbarInfo(event.message))
            }

            HomeViewModel.UiEvent.RequestLocationPermission -> {
                locationPermissionResultLauncher.launch(permissions)
            }

            is HomeViewModel.UiEvent.GoToDetailsScreen -> {
                navController.navigate(Screen.DetailsScreen.withArgs(event.fsqId))
            }

            is HomeViewModel.UiEvent.GoToAddPlanScreen -> {
                navController.navigate(Screen.AddPlanScreen.withArgs(event.fsqId))
            }
        }
    }

    if (state.showLocationPermissionDialog) {
        PermissionDialog(
            permissionTextProvider = LocationPermissionTextProvider(),
            isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(PermissionChecker.COARSE_LOCATION_PERMISSION),
            onDismiss = {
                onEvent(HomeViewModel.Event.DismissLocationDialog)
            },
            onOkClick = {
                onEvent(HomeViewModel.Event.DismissLocationDialog)
                locationPermissionResultLauncher.launch(permissions)
            },
            onGoToAppSettingsClick = {
                onEvent(HomeViewModel.Event.DismissLocationDialog)
                activity.openAppSettings()
            },
        )
    }

    val items: List<HomeFeedItem> = remember(state.places) {
        buildList {
            add(HomeFeedItem.Weather)
            addAll(state.places.map { HomeFeedItem.Place(it) })
        }
    }


    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        PullToRefreshLazyColumn(
            items = items,
            content = { feedItem ->
                when (feedItem) {

                    is HomeFeedItem.Weather -> {
                        WeatherItem(
                            state = state, onEvent = onEvent,
                        )
                    }

                    is HomeFeedItem.Place -> {
                        PlaceCard(
                            placeModel = feedItem.placeModel,
                            goToDetails = {
                                onEvent(HomeViewModel.Event.OnVenueClicked(feedItem.placeModel.fsqId))
                            },
                            addToPlans = {
                                onEvent(HomeViewModel.Event.AddToPlans(feedItem.placeModel.fsqId))
                            }
                        )
                    }

                }
            },
            isRefreshing = state.isRefreshing,
            onRefresh = {
                Timber.tag("Location").i("-1")
                onEvent(HomeViewModel.Event.Refresh)
            }
        )

        if (state.places.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.no_places_nearby)
            )
        }

    }

}

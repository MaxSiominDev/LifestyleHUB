package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.maxsiomin.prodhse.core.CollectFlow
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.core.SnackbarInfo
import dev.maxsiomin.prodhse.core.UiText
import dev.maxsiomin.prodhse.core.location.PermissionChecker
import dev.maxsiomin.prodhse.core.openAppSettings
import dev.maxsiomin.prodhse.core.ui.LocationPermissionTextProvider
import dev.maxsiomin.prodhse.core.ui.PermissionDialog
import dev.maxsiomin.prodhse.feature.venues.R
import dev.maxsiomin.prodhse.feature.weather.presentation.weatherUi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VenuesScreen(
    state: VenuesViewModel.State,
    onEvent: (VenuesViewModel.Event) -> Unit,
    eventsFlow: Flow<VenuesViewModel.UiEvent>,
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
                    VenuesViewModel.Event.LocationPermissionResult(
                        coarseIsGranted = results[PermissionChecker.COARSE_LOCATION_PERMISSION]!!,
                    )
                )
            }
        )

    val pullToRefreshState = rememberPullToRefreshState()

    CollectFlow(eventsFlow) { event ->
        when (event) {
            is VenuesViewModel.UiEvent.FetchingError -> {
                showSnackbar(SnackbarInfo(UiText.DynamicString(event.message)))
            }

            VenuesViewModel.UiEvent.RequestLocationPermission -> {
                locationPermissionResultLauncher.launch(permissions)
            }
        }
    }

    if (state.showLocationPermissionDialog) {
        PermissionDialog(
            permissionTextProvider = LocationPermissionTextProvider(),
            isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(PermissionChecker.COARSE_LOCATION_PERMISSION),
            onDismiss = {
                onEvent(VenuesViewModel.Event.DismissLocationDialog)
            },
            onOkClick = {
                onEvent(VenuesViewModel.Event.DismissLocationDialog)
                locationPermissionResultLauncher.launch(permissions)
            },
            onGoToAppSettingsClick = {
                onEvent(VenuesViewModel.Event.DismissLocationDialog)
                activity.openAppSettings()
            },
        )
    }

    val items: List<FeedItem> = buildList {
        add(FeedItem.Weather)
        addAll(state.places.map { FeedItem.Venue(it) })
    }


    Box(
        Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items) { feedItem ->
                when (feedItem) {

                    is FeedItem.Weather -> {
                        val updateCallback =
                            weatherUi(
                                showSnackbar = showSnackbar,
                                endRefresh = pullToRefreshState::endRefresh
                            )
                        if (pullToRefreshState.isRefreshing) {
                            updateCallback.invoke()
                            onEvent(VenuesViewModel.Event.Refresh)
                        }
                    }

                    is FeedItem.Venue -> {
                        VenueCard(placeModel = feedItem.placeModel)
                    }

                }
            }
        }

        if (state.places.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.no_places_nearby)
            )
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
        )
    }

}

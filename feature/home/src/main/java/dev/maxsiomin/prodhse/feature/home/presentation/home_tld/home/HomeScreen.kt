package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.maxsiomin.common.extensions.openAppSettings
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.common.presentation.SnackbarInfo
import dev.maxsiomin.common.presentation.components.PermissionDialog
import dev.maxsiomin.common.presentation.components.PullToRefreshLazyColumn
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.core.location.PermissionChecker
import dev.maxsiomin.prodhse.core.presentation.LocationPermissionTextProvider
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.Flow

@Composable
internal fun HomeScreenRoot(
    navController: NavController,
    showSnackbar: SnackbarCallback,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val activity = LocalContext.current as ComponentActivity

    val state by viewModel.state.collectAsStateWithLifecycle()

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
                viewModel.onEvent(
                    HomeViewModel.Event.LocationPermissionResult(
                        coarseIsGranted = results[PermissionChecker.COARSE_LOCATION_PERMISSION]!!,
                    )
                )
            }
        )

    CollectFlow(viewModel.effectFlow) { effect ->
        when (effect) {
            is HomeViewModel.Effect.ShowMessage -> showSnackbar(SnackbarInfo(effect.message))

            HomeViewModel.Effect.RequestLocationPermission -> {
                locationPermissionResultLauncher.launch(permissions)
            }

            is HomeViewModel.Effect.GoToDetailsScreen -> {
                navController.navigate(Screen.DetailsScreen.withArgs(effect.fsqId))
            }

            is HomeViewModel.Effect.GoToAddPlanScreen -> {
                navController.navigate(Screen.AddPlanScreen.withArgs(effect.fsqId))
            }

            HomeViewModel.Effect.GoToAppSettings -> activity.openAppSettings()
        }
    }

    if (state.showLocationPermissionDialog) {
        PermissionDialog(
            permissionTextProvider = LocationPermissionTextProvider,
            isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(
                PermissionChecker.COARSE_LOCATION_PERMISSION
            ),
            onDismiss = {
                viewModel.onEvent(HomeViewModel.Event.LocationDialog.Dismissed)
            },
            onOkClick = {
                viewModel.onEvent(HomeViewModel.Event.LocationDialog.Confirmed)
            },
            onGoToAppSettingsClick = {
                viewModel.onEvent(HomeViewModel.Event.LocationDialog.GoToAppSettings)
            },
        )
    }

    HomeScreen(state = state, onEvent = viewModel::onEvent)

}

@Composable
private fun HomeScreen(state: HomeViewModel.State, onEvent: (HomeViewModel.Event) -> Unit) {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        PullToRefreshLazyColumn(
            isRefreshing = state.isRefreshing,
            onRefresh = {
                onEvent(HomeViewModel.Event.Refresh)
            },
        ) {
            item {
                WeatherItem(
                    state = state, onEvent = onEvent,
                )
            }
            items(state.places) { place ->
                PlaceCard(
                    place = place,
                    goToDetails = {
                        onEvent(HomeViewModel.Event.OnVenueClicked(place.fsqId))
                    },
                    addToPlans = {
                        onEvent(HomeViewModel.Event.AddToPlans(place.fsqId))
                    }
                )
            }
        }

        when {
            state.places.isEmpty() && state.placesStatus is HomeViewModel.PlacesStatus.Success -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.no_places_nearby)
                )
            }

            state.placesStatus is HomeViewModel.PlacesStatus.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }

            state.placesStatus is HomeViewModel.PlacesStatus.Error -> {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    text = state.placesStatus.message.asString(),
                )
            }
        }

    }
}

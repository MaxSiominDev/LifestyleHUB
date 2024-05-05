package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

import android.location.Location
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.asErrorUiText
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.domain.use_case.places.BatchAddPhotosToPlacesUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.other.CheckLocationPermissionUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.other.GetCurrentLocationUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.weather.GetCurrentWeatherUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.weather.GetDefaultWeatherUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.places.GetPlacesNearbyUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getDefaultWeatherUseCase: GetDefaultWeatherUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getPlacesNearbyUseCase: GetPlacesNearbyUseCase,
    private val batchAddPhotosToPlacesUseCase: BatchAddPhotosToPlacesUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
) : StatefulViewModel<HomeViewModel.State, HomeViewModel.Effect, HomeViewModel.Event>() {

    private var locationIsRefreshing = false
        set(value) {
            field = value
            _state.update {
                it.copy(isRefreshing = value || weatherIsRefreshing || placesIsRefreshing)
            }
        }

    private var weatherIsRefreshing = false
        set(value) {
            field = value
            _state.update {
                it.copy(isRefreshing = locationIsRefreshing || value || placesIsRefreshing)
            }
        }

    private var placesIsRefreshing = false
        set(value) {
            field = value
            _state.update {
                it.copy(isRefreshing = locationIsRefreshing || weatherIsRefreshing || value)
            }
        }


    sealed class WeatherStatus {
        // Show shimmering loader
        data object Loading : WeatherStatus()

        // Show actual weather data
        data object Success : WeatherStatus()

        // Show that something went wrong
        data object Error : WeatherStatus()
    }

    sealed class PlacesStatus {
        // Show circular progress indicator at bottom end
        data object Loading : PlacesStatus()

        // Show actual places data
        data object Success : PlacesStatus()

        // Show that something went wrong
        data class Error(val message: UiText) : PlacesStatus()
    }

    data class State(
        val places: List<Place> = listOf(),
        val placesStatus: PlacesStatus = PlacesStatus.Loading,

        val weather: Weather,
        val weatherStatus: WeatherStatus = WeatherStatus.Loading,
        val weatherIsExpanded: Boolean = true,

        val isRefreshing: Boolean = false,
        val showLocationPermissionDialog: Boolean = false,
    )

    override val _state = MutableStateFlow(
        State(weather = getDefaultWeatherUseCase())
    )


    sealed class Effect {
        data class ShowMessage(val message: UiText) : Effect()
        data object RequestLocationPermission : Effect()
        data class GoToDetailsScreen(val fsqId: String) : Effect()
        data class GoToAddPlanScreen(val fsqId: String) : Effect()
        data object GoToAppSettings : Effect()
    }


    sealed class Event {
        data object Refresh : Event()
        data class LocationPermissionResult(val coarseIsGranted: Boolean) : Event()

        data class OnVenueClicked(val fsqId: String) : Event()
        data class AddToPlans(val fsqId: String) : Event()
        data object ExpandStateChanged : Event()

        sealed class LocationDialog : Event() {
            data object Confirmed : LocationDialog()
            data object GoToAppSettings : LocationDialog()
            data object Dismissed : LocationDialog()
        }
    }

    init {
        refresh()
    }


    override fun onEvent(event: Event) {
        when (event) {

            Event.Refresh -> refresh()

            is Event.LocationPermissionResult -> {
                if (event.coarseIsGranted.not()) {
                    _state.update {
                        it.copy(showLocationPermissionDialog = true)
                    }
                    return
                }
                refresh()
            }

            Event.LocationDialog.Dismissed -> _state.update {
                it.copy(showLocationPermissionDialog = false)
            }

            Event.LocationDialog.GoToAppSettings -> {
                _state.update {
                    it.copy(showLocationPermissionDialog = false)
                }
                onEffect(Effect.GoToAppSettings)
            }

            Event.LocationDialog.Confirmed -> {
                _state.update {
                    it.copy(showLocationPermissionDialog = false)
                }
                onEffect(Effect.RequestLocationPermission)
            }

            is Event.OnVenueClicked -> onEffect(Effect.GoToDetailsScreen(fsqId = event.fsqId))

            is Event.AddToPlans -> onEffect(Effect.GoToAddPlanScreen(fsqId = event.fsqId))

            Event.ExpandStateChanged -> _state.update {
                it.copy(weatherIsExpanded = it.weatherIsExpanded.not())
            }
        }
    }

    private fun refresh() {
        locationIsRefreshing = true

        viewModelScope.launch {
            // I need this delay so that pull refresh lazy column had enough time to react to state change
            delay(1)

            if (checkLocationPermissionUseCase().not()) {
                locationIsRefreshing = false
                onEffect(Effect.RequestLocationPermission)
                _state.update {
                    it.copy(
                        weatherStatus = WeatherStatus.Error,
                        placesStatus = PlacesStatus.Error(UiText.StringResource(R.string.missing_permission))
                    )
                }
                return@launch
            }

            val locationResource = getCurrentLocationUseCase()
            when (locationResource) {
                is Resource.Error -> {
                    locationIsRefreshing = false
                    _state.update {
                        it.copy(
                            weatherStatus = WeatherStatus.Error,
                            placesStatus = PlacesStatus.Error(locationResource.asErrorUiText())
                        )
                    }
                    onEffect(Effect.ShowMessage(locationResource.asErrorUiText()))
                }

                is Resource.Success -> {
                    val locationData = requireNotNull(locationResource.data) {
                        "Location data is null even though Resource.Success is called"
                    }
                    refreshPlaces(locationData)
                    refreshWeather(locationData)
                    // Important to call this after `refreshPlaces` and `refreshWeather` are called
                    // to avoid flickering progress bar
                    locationIsRefreshing = false
                }
            }
        }
    }

    private fun refreshPlaces(location: Location) {

        _state.update {
            it.copy(places = emptyList(), placesStatus = PlacesStatus.Loading)
        }
        placesIsRefreshing = true

        viewModelScope.launch {
            val lat = location.latitude
            val lon = location.longitude

            val placesNearbyResource = getPlacesNearbyUseCase(lat = lat, lon = lon)
            placesIsRefreshing = false
            when (placesNearbyResource) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(placesStatus = PlacesStatus.Error(placesNearbyResource.asErrorUiText()))
                    }
                    onEffect(Effect.ShowMessage(placesNearbyResource.asErrorUiText()))
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            places = placesNearbyResource.data,
                            placesStatus = PlacesStatus.Success
                        )
                    }
                    loadPhotos(placesNearbyResource.data)
                }
            }
        }
    }

    private fun loadPhotos(places: List<Place>) {
        viewModelScope.launch {
            val placesWithPhotos = batchAddPhotosToPlacesUseCase(
                places = places,
                placeholderUrl = IMAGE_PLACEHOLDER
            )
            _state.update {
                it.copy(places = placesWithPhotos)
            }
        }
    }

    private fun refreshWeather(location: Location) {
        weatherIsRefreshing = true
        _state.update {
            it.copy(weatherStatus = WeatherStatus.Loading)
        }

        viewModelScope.launch {
            val lat = location.latitude
            val lon = location.longitude

            val weatherResource = getCurrentWeatherUseCase(lat = lat, lon = lon)
            weatherIsRefreshing = false
            when (weatherResource) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(weatherStatus = WeatherStatus.Error)
                    }
                    onEffect(Effect.ShowMessage(weatherResource.asErrorUiText()))
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            weather = weatherResource.data,
                            weatherStatus = WeatherStatus.Success,
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val IMAGE_PLACEHOLDER = "file:///android_asset/placeholder.png"
    }
}

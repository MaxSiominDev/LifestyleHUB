package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.asErrorUiText
import dev.maxsiomin.prodhse.core.location.PermissionChecker
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.home.data.mappers.WeatherDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.domain.repository.LocationRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val placesRepo: PlacesRepository,
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationRepository,
    private val localeManager: LocaleManager,
    private val permissionChecker: PermissionChecker,
) : ViewModel() {

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
        // TODO
        data object Loading : PlacesStatus()

        // Show actual places data
        data object Success : PlacesStatus()

        // Show that something went wrong
        data class Error(val message: UiText) : PlacesStatus()
    }

    data class State(
        val places: List<Place> = listOf(),
        val placesStatus: PlacesStatus = PlacesStatus.Loading,

        val weather: Weather = WeatherDtoToUiModelMapper().invoke(CurrentWeatherResponse()),
        val weatherStatus: WeatherStatus = WeatherStatus.Loading,
        val weatherIsExpanded: Boolean = true,

        val isRefreshing: Boolean = false,
        val showLocationPermissionDialog: Boolean = false,
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    sealed class UiEvent {
        data class ShowMessage(val message: UiText) : UiEvent()
        data object RequestLocationPermission : UiEvent()
        data class GoToDetailsScreen(val fsqId: String) : UiEvent()
        data class GoToAddPlanScreen(val fsqId: String) : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()


    sealed class Event {
        data object Refresh : Event()
        data class LocationPermissionResult(val coarseIsGranted: Boolean) : Event()
        data object DismissLocationDialog : Event()
        data class OnVenueClicked(val fsqId: String) : Event()
        data class AddToPlans(val fsqId: String) : Event()
        data object ExpandStateChanged : Event()
    }

    init {
        refresh()
    }


    fun onEvent(event: Event) {
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

            Event.DismissLocationDialog -> _state.update {
                it.copy(showLocationPermissionDialog = false)
            }

            is Event.OnVenueClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.GoToDetailsScreen(fsqId = event.fsqId))
            }

            is Event.AddToPlans -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.GoToAddPlanScreen(fsqId = event.fsqId))
            }

            Event.ExpandStateChanged -> {
                _state.update {
                    it.copy(weatherIsExpanded = it.weatherIsExpanded.not())
                }
            }
        }
    }

    private fun refresh() {
        locationIsRefreshing = true

        viewModelScope.launch {
            // I need this delay so that pull refresh lazy column had enough time to react to state change
            delay(1)

            if (permissionChecker.hasPermission(PermissionChecker.COARSE_LOCATION_PERMISSION).not()) {
                locationIsRefreshing = false
                viewModelScope.launch {
                    _eventsFlow.send(UiEvent.RequestLocationPermission)
                }
                _state.update {
                    it.copy(
                        weatherStatus = WeatherStatus.Error,
                        placesStatus = PlacesStatus.Error(UiText.DynamicString("TODO"))
                    )
                }
                return@launch
            }

            val locationResource = locationRepo.getCurrentLocation()
            when (locationResource) {
                is Resource.Error -> {
                    locationIsRefreshing = false
                    _state.update {
                        it.copy(
                            weatherStatus = WeatherStatus.Error,
                            placesStatus = PlacesStatus.Error(locationResource.asErrorUiText())
                        )
                    }
                    _eventsFlow.send(UiEvent.ShowMessage(locationResource.asErrorUiText()))
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
            val lat = location.latitude.toString()
            val lon = location.longitude.toString()
            val lang = localeManager.getLocaleLanguage()

            val placesNearbyResource = placesRepo.getPlacesNearby(lat = lat, lon = lon, lang = lang)
            placesIsRefreshing = false
            when (placesNearbyResource) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(placesStatus = PlacesStatus.Error(placesNearbyResource.asErrorUiText()))
                    }
                    _eventsFlow.send(UiEvent.ShowMessage(placesNearbyResource.asErrorUiText()))
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
        val placeHolderUrl = "file:///android_asset/placeholder.png"
        viewModelScope.launch {
            val placesWithPhoto = places.map {
                async {
                    var photo: Photo? = null
                    val photosResource = placesRepo.getPhotos(id = it.fsqId)
                    when (photosResource) {
                        is Resource.Error -> Unit
                        is Resource.Success -> {
                            photo = photosResource.data.firstOrNull()
                        }
                    }
                    it.copy(photoUrl = photo?.url ?: placeHolderUrl)
                }
            }.awaitAll()
            _state.update {
                it.copy(places = placesWithPhoto)
            }
        }
    }

    private fun refreshWeather(location: Location) {
        weatherIsRefreshing = true
        _state.update {
            it.copy(weatherStatus = WeatherStatus.Loading)
        }

        viewModelScope.launch {
            val lat = location.latitude.toString()
            val lon = location.longitude.toString()
            val lang = localeManager.getLocaleLanguage()

            val weatherResource = weatherRepo.getCurrentWeather(lat = lat, lon = lon, lang = lang)
            weatherIsRefreshing = false
            when (weatherResource) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(weatherStatus = WeatherStatus.Error)
                    }
                    _eventsFlow.send(UiEvent.ShowMessage(weatherResource.asErrorUiText()))
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
}

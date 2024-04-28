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
import dev.maxsiomin.prodhse.feature.home.domain.Photo
import dev.maxsiomin.prodhse.feature.home.domain.Place
import dev.maxsiomin.prodhse.feature.home.domain.Weather
import dev.maxsiomin.prodhse.feature.home.domain.repository.LocationRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
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

    private var weatherIsRefreshing = false
        set(value) {
            field = value
            state = state.copy(isRefreshing = value || placesIsRefreshing || locationIsRefreshing)
        }

    private var placesIsRefreshing = false
        set(value) {
            field = value
            state = state.copy(isRefreshing = weatherIsRefreshing || value || locationIsRefreshing)
        }

    private var locationIsRefreshing = false
        set(value) {
            field = value
            state = state.copy(isRefreshing = weatherIsRefreshing || placesIsRefreshing || value)
        }


    sealed class WeatherStatus {
        // Show shimmering loader
        data object Loading : WeatherStatus()

        // Show actual weather data
        data object Success : WeatherStatus()

        // Show that something went wrong
        data object Error : WeatherStatus()
    }

    data class State(
        val places: List<Place> = listOf(),
        val isRefreshing: Boolean = false,
        val showLocationPermissionDialog: Boolean = false,
        val invokeWeatherCallback: Boolean = false,
        val weather: Weather = WeatherDtoToUiModelMapper().invoke(CurrentWeatherResponse()),
        val weatherStatus: WeatherStatus = WeatherStatus.Loading,
        val weatherIsExpanded: Boolean = true,
    )

    var state by mutableStateOf(State())
        private set


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
        data object UpdateWeatherMessageAccepted : Event()
        data class AddToPlans(val fsqId: String) : Event()
        data object ExpandStateChanged : Event()
    }

    fun onEvent(event: Event) {
        when (event) {

            Event.Refresh -> refresh()

            is Event.LocationPermissionResult -> {
                if (event.coarseIsGranted.not()) {
                    state = state.copy(showLocationPermissionDialog = true)
                    return
                }
                state = state.copy(invokeWeatherCallback = true)
                refresh()
            }

            Event.DismissLocationDialog -> state = state.copy(showLocationPermissionDialog = false)

            is Event.OnVenueClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.GoToDetailsScreen(fsqId = event.fsqId))
            }

            Event.UpdateWeatherMessageAccepted -> state = state.copy(invokeWeatherCallback = false)

            is Event.AddToPlans -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.GoToAddPlanScreen(fsqId = event.fsqId))
            }

            Event.ExpandStateChanged -> {
                state = state.copy(weatherIsExpanded = state.weatherIsExpanded.not())
            }
        }
    }


    init {
        refresh()
    }

    private fun refresh() {

        if (permissionChecker.hasPermission(PermissionChecker.COARSE_LOCATION_PERMISSION).not()) {
            viewModelScope.launch {
                _eventsFlow.send(UiEvent.RequestLocationPermission)
            }
            state = state.copy(weatherStatus = WeatherStatus.Error)
            return
        }

        locationIsRefreshing = true

        viewModelScope.launch {
            val location = locationRepo.getCurrentLocation()
            when (location) {
                is Resource.Error -> {
                    locationIsRefreshing = false
                    state = state.copy(weatherStatus = WeatherStatus.Error)
                    _eventsFlow.send(UiEvent.ShowMessage(location.asErrorUiText()))
                }

                is Resource.Success -> {
                    val locationData = requireNotNull(location.data) {
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

        state = state.copy(places = emptyList())
        placesIsRefreshing = true

        viewModelScope.launch {
            val lat = location.latitude.toString()
            val lon = location.longitude.toString()
            val lang = localeManager.getLocaleLanguage()

            placesRepo.getPlacesNearby(lat = lat, lon = lon, lang = lang).collect { resource ->
                placesIsRefreshing = false

                when (resource) {
                    is Resource.Error -> _eventsFlow.send(UiEvent.ShowMessage(resource.asErrorUiText()))

                    is Resource.Success -> {
                        state = state.copy(places = resource.data)
                        loadPhotos(resource.data)
                    }
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
                    placesRepo.getPhotos(id = it.fsqId).collect { photosResource ->
                        when (photosResource) {
                            is Resource.Error -> Unit
                            is Resource.Success -> {
                                photo = photosResource.data.firstOrNull()
                            }
                        }
                    }
                    it.copy(photoUrl = photo?.url ?: placeHolderUrl)
                }
            }.awaitAll()
            state = state.copy(places = placesWithPhoto)
        }
    }

    private fun refreshWeather(location: Location) {
        weatherIsRefreshing = true
        state = state.copy(weatherStatus = WeatherStatus.Loading)

        viewModelScope.launch {
            val lat = location.latitude.toString()
            val lon = location.longitude.toString()
            val lang = localeManager.getLocaleLanguage()

            weatherRepo.getCurrentWeather(lat = lat, lon = lon, lang = lang).collect { resource ->
                weatherIsRefreshing = false
                when (resource) {
                    is Resource.Error -> {
                        state = state.copy(weatherStatus = WeatherStatus.Error)
                        _eventsFlow.send(UiEvent.ShowMessage(resource.asErrorUiText()))
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            weather = resource.data,
                            weatherStatus = WeatherStatus.Success,
                        )
                    }
                }
            }
        }
    }
}

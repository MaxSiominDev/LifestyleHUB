package dev.maxsiomin.prodhse.feature.home.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.core.location.LocationTracker
import dev.maxsiomin.prodhse.core.location.PermissionChecker
import dev.maxsiomin.prodhse.feature.home.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.home.domain.PlaceModel
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val repo: PlacesRepository,
    private val locationTracker: LocationTracker,
    private val localeManager: LocaleManager,
    private val permissionChecker: PermissionChecker,
) : ViewModel() {

    /** If you want loading widget to stay forever (for testing purposes only) set this to true */
    private val forceLoading = false

    data class State(
        val places: List<PlaceModel> = listOf(),
        val isRefreshing: Boolean = false,
        val showLocationPermissionDialog: Boolean = false,
        val invokeWeatherCallback: Boolean = false,
    )

    var state by mutableStateOf(State())
        private set

    sealed class UiEvent {
        data class FetchingError(val message: String) : UiEvent()
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
    }

    fun onEvent(event: Event) {
        when (event) {

            Event.Refresh -> refreshPlaces()

            is Event.LocationPermissionResult -> {
                if (event.coarseIsGranted.not()) {
                    state = state.copy(showLocationPermissionDialog = true)
                    return
                }
                state = state.copy(invokeWeatherCallback = true)
                refreshPlaces()
            }

            Event.DismissLocationDialog -> state = state.copy(showLocationPermissionDialog = false)

            is Event.OnVenueClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.GoToDetailsScreen(fsqId = event.fsqId))
            }

            Event.UpdateWeatherMessageAccepted -> state = state.copy(invokeWeatherCallback = false)

            is Event.AddToPlans -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.GoToAddPlanScreen(fsqId = event.fsqId))
            }
        }
    }


    init {
        refreshPlaces()
    }


    private fun refreshPlaces() {

        if (permissionChecker.hasPermission(PermissionChecker.COARSE_LOCATION_PERMISSION).not()) {
            viewModelScope.launch {
                _eventsFlow.send(UiEvent.RequestLocationPermission)
            }
            return
        }

        state = state.copy(isRefreshing = true, places = emptyList())
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation() ?: kotlin.run {
                _eventsFlow.send(UiEvent.FetchingError("Location cannot be retrieved"))
                return@launch
            }
            val lat = location.latitude.toString()
            val lon = location.longitude.toString()
            val lang = localeManager.getLocaleLanguage()
            getCurrentWeather(lat = lat, lon = lon, lang = lang)
        }
    }

    private suspend fun getCurrentWeather(lat: String, lon: String, lang: String) {
        repo.getPlacesNearby(lat = lat, lon = lon, lang = lang).collect { placesResource ->
            when (placesResource) {
                is Resource.Loading -> {
                    state = state.copy(isRefreshing = placesResource.isLoading || forceLoading)
                }

                is Resource.Error -> {
                    _eventsFlow.send(UiEvent.FetchingError("Places info is unavailable"))
                }

                is Resource.Success -> {
                    state = state.copy(places = placesResource.data)
                    loadPhotos(placesResource.data)
                }
            }
        }
    }

    private fun loadPhotos(places: List<PlaceModel>) {
        val placeHolderUrl = "file:///android_asset/placeholder.png"
        viewModelScope.launch {
            val placesWithPhoto = places.map {
                async {
                    var photoModel: PhotoModel? = null
                    repo.getPhotos(id = it.fsqId).collect { photosResource ->
                        when (photosResource) {
                            is Resource.Loading -> {
                                Timber.i("Photo data is loading...")
                            }

                            is Resource.Error -> {
                                Timber.e(photosResource.exception)
                            }

                            is Resource.Success -> {
                                photoModel = photosResource.data.firstOrNull()
                            }
                        }
                    }
                    it.copy(photoUrl = photoModel?.url ?: placeHolderUrl)
                }
            }.awaitAll()
            state = state.copy(places = placesWithPhoto)
        }
    }


}
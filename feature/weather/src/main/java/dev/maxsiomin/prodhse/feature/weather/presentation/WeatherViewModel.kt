package dev.maxsiomin.prodhse.feature.weather.presentation

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.core.location.LocationClient
import dev.maxsiomin.prodhse.feature.weather.data.mappers.WeatherDtoToUiModel
import dev.maxsiomin.prodhse.feature.weather.domain.WeatherModel
import dev.maxsiomin.prodhse.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository,
    private val locationClient: LocationClient,
) : ViewModel() {

    init {
        refreshWeather()
    }

    data class State(
        val weather: WeatherModel? = null,
        val weatherStatus: WeatherStatus = WeatherStatus.Loading,
    )

    sealed class WeatherStatus {
        // TODO Show shimmering effect, for now same as Error
        data object Loading : WeatherStatus()
        // Show actual weather data
        data object Success : WeatherStatus()
        // Show that something went wrong
        data object Error : WeatherStatus()
    }

    var state by mutableStateOf(State())
        private set

    sealed class Event {
        data object RefreshWeather : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.RefreshWeather -> { refreshWeather() }
        }
    }

    sealed class UiEvent {
        data class FetchingError(val message: String) : UiEvent()
    }

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private fun refreshWeather() {
        viewModelScope.launch {
            val location = try {
                getCurrentLocation()
            } catch (e: LocationClient.LocationException) {
                _eventFlow.send(UiEvent.FetchingError(e.message))
                return@launch
            }
            val lat = location.latitude.toString()
            val lon = location.longitude.toString()
            val lang = "ru"
            getCurrentWeather(lat = lat, lon = lon, lang = lang)
        }
    }

    @Throws(LocationClient.LocationException::class)
    private suspend fun getCurrentLocation(): Location {
        return locationClient.getLocation()
    }

    private suspend fun getCurrentWeather(lat: String, lon: String, lang: String) {
        repo.getCurrentWeather(lat = lat, lon = lon, lang = lang).collect { response ->
            when (response) {
                is Resource.Loading -> {
                    if (response.isLoading) {
                        state = state.copy(weatherStatus = WeatherStatus.Loading)
                    }
                }
                is Resource.Error -> {
                    state = state.copy(weatherStatus = WeatherStatus.Error)
                    _eventFlow.send(UiEvent.FetchingError("Weather info is unavailable"))
                }
                is Resource.Success -> {
                    val weatherModel = WeatherDtoToUiModel().invoke(response.data)
                    state = state.copy(weather = weatherModel, weatherStatus = WeatherStatus.Success)
                }
            }
        }
    }

}
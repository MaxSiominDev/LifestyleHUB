package dev.maxsiomin.prodhse.feature.weather.presentation

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.LocaleManager
import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.core.location.LocationClient
import dev.maxsiomin.prodhse.feature.weather.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.weather.data.mappers.WeatherDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.weather.domain.TemperatureInfo
import dev.maxsiomin.prodhse.feature.weather.domain.WeatherCondition
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
    private val localeManager: LocaleManager,
) : ViewModel() {

    /** If you want loading widget to stay forever (for testing purposes only) set this to true */
    private val forceLoading = false

    init {
        refreshWeather()
    }

    data class State(
        val weather: WeatherModel? = null,
        val weatherStatus: WeatherStatus = WeatherStatus.Loading,
        val isExpanded: Boolean = true,
    )

    sealed class WeatherStatus {
        // TODO Show shimmering effect
        data object Loading : WeatherStatus()

        // Show actual weather data
        data object Success : WeatherStatus()

        // Show that something went wrong
        data object Error : WeatherStatus()
    }

    var state by mutableStateOf(State())
        private set

    private val previousIsNight get() = state.weather?.weatherCondition?.isNight
    val emptyCardContent get() =
        WeatherDtoToUiModelMapper().invoke(CurrentWeatherResponse(), previousIsNight)

    var endRefreshCallback: (() -> Unit)? = null

    sealed class Event {
        data object RefreshWeather : Event()
        data object ExpandStateChanged : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.RefreshWeather -> {
                refreshWeather()
            }

            Event.ExpandStateChanged -> {
                state = state.copy(isExpanded = state.isExpanded.not())
            }
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
                endRefreshCallback?.invoke()
                state = state.copy(weatherStatus = WeatherStatus.Error)
                return@launch
            }
            val lat = location.latitude.toString()
            val lon = location.longitude.toString()
            val lang = localeManager.getLocaleLanguage()
            getCurrentWeather(lat = lat, lon = lon, lang = lang)
        }
    }

    @Throws(LocationClient.LocationException::class)
    private suspend fun getCurrentLocation(): Location {
        return locationClient.getLocation()
    }

    private suspend fun getCurrentWeather(lat: String, lon: String, lang: String) {
        repo.getCurrentWeather(lat = lat, lon = lon, lang = lang).collect { response ->
            endRefreshCallback?.invoke()
            when (response) {
                is Resource.Loading -> {
                    if (response.isLoading || forceLoading) {
                        state = state.copy(weatherStatus = WeatherStatus.Loading)
                    }
                }

                is Resource.Error -> {
                    state = state.copy(weatherStatus = WeatherStatus.Error)
                    _eventFlow.send(UiEvent.FetchingError("Weather info is unavailable"))
                }

                is Resource.Success -> {
                    val weatherModel =
                        WeatherDtoToUiModelMapper().invoke(response.data, previousIsNight)
                    state =
                        state.copy(weather = weatherModel, weatherStatus = WeatherStatus.Success)
                }
            }
        }
    }

}
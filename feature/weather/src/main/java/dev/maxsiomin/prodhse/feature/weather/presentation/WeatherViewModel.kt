package dev.maxsiomin.prodhse.feature.weather.presentation

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.core.location.LocationTracker
import dev.maxsiomin.prodhse.feature.weather.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.weather.data.mappers.WeatherDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.weather.domain.WeatherModel
import dev.maxsiomin.prodhse.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val localeManager: LocaleManager,
) : ViewModel() {

    /** If you want loading widget to stay forever (for testing purposes only) set this to true */
    private val forceLoading = false

    data class State(
        val weather: WeatherModel? = null,
        val weatherStatus: WeatherStatus = WeatherStatus.Loading,
        val isExpanded: Boolean = true,
    )

    sealed class WeatherStatus {
        // Show shimmering loader
        data object Loading : WeatherStatus()

        // Show actual weather data
        data object Success : WeatherStatus()

        // Show that something went wrong
        data object Error : WeatherStatus()
    }

    var state by mutableStateOf(State())
        private set

    init {
        refreshWeather()
    }

    /** Without this workaround, when you refresh widget, it becomes day-themed until it is loaded.
     *  This is not a problem during daytime but at night color changes every time you refresh the widget.
     *  So I pass previous state of weather for the duration of refreshing
     */
    private val previousIsNight get() = state.weather?.weatherCondition?.isNight
    val emptyCardContent
        get() = WeatherDtoToUiModelMapper().invoke(CurrentWeatherResponse(), previousIsNight)

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

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()

    private fun refreshWeather() {
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation() ?: kotlin.run {
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

    private suspend fun getCurrentWeather(lat: String, lon: String, lang: String) {
        repo.getCurrentWeather(lat = lat, lon = lon, lang = lang)
            .collect { weatherModelResource ->
                endRefreshCallback?.invoke()
                when (weatherModelResource) {
                    is Resource.Loading -> {
                        if (weatherModelResource.isLoading || forceLoading) {
                            state = state.copy(weatherStatus = WeatherStatus.Loading)
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(weatherStatus = WeatherStatus.Error)
                        _eventsFlow.send(UiEvent.FetchingError("Weather info is unavailable"))
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            weather = weatherModelResource.data,
                            weatherStatus = WeatherStatus.Success
                        )
                    }
                }
            }
    }

}
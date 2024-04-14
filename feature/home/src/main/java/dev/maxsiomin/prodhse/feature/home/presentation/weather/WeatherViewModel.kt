package dev.maxsiomin.prodhse.feature.home.presentation.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.domain.Resource
import dev.maxsiomin.prodhse.core.location.LocationTracker
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.home.data.mappers.WeatherDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.home.domain.WeatherModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository,
    private val locationTracker: LocationTracker,
    private val localeManager: LocaleManager,
) : ViewModel() {

    data class State(
        val weather: WeatherModel = WeatherDtoToUiModelMapper().invoke(CurrentWeatherResponse()),
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
        state = state.copy(weatherStatus = WeatherStatus.Loading)
        repo.getCurrentWeather(lat = lat, lon = lon, lang = lang).collect { weatherModelResource ->
            endRefreshCallback?.invoke()
            when (weatherModelResource) {
                is Resource.Error -> {
                    state = state.copy(weatherStatus = WeatherStatus.Error)
                    _eventsFlow.send(UiEvent.FetchingError("Weather info is unavailable"))
                }

                is Resource.Success -> {
                    state = state.copy(
                        weather = weatherModelResource.data,
                        weatherStatus = WeatherStatus.Success,
                    )
                }
            }
        }
    }

}
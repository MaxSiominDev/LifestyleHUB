package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class VenuesViewModel @Inject constructor() : ViewModel() {

    data class State(
        val expandWeather: Boolean = true,
    )

    var state by mutableStateOf(State())
        private set


    sealed class Event {
        data object ExpandTextClicked : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.ExpandTextClicked -> state = state.copy(expandWeather = state.expandWeather.not())
        }
    }

}
package dev.maxsiomin.prodhse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    data class State(
        val selectedBottomNavBarItemIndex: Int,
    )

    var state by mutableStateOf(State(selectedBottomNavBarItemIndex = 0))
        private set

    sealed class Event {
        data class BottomNavBarDestinationChanged(val newIndex: Int) : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.BottomNavBarDestinationChanged -> {
                state = state.copy(selectedBottomNavBarItemIndex = event.newIndex)
            }
        }
    }

}
package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuccessfulRegistrationViewModel @Inject constructor(): ViewModel() {

    sealed class UiEvent {
        data object NavigateToLoginScreen : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()


    sealed class Event {
        data object LoginClicked : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.LoginClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.NavigateToLoginScreen)
            }
        }
    }

}
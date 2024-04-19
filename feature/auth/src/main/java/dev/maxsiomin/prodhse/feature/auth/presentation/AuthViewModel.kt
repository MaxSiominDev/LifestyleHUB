package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authManager: AuthManager) : ViewModel() {

    sealed class UiEvent {
        data object NavigateToLoginScreen : UiEvent()
        data object NavigateToSignupScreen : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()


    sealed class Event {
        data object LoginClicked : Event()
        data object SignupClicked : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.LoginClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.NavigateToLoginScreen)
            }
            Event.SignupClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.NavigateToSignupScreen)
            }
        }
    }

}
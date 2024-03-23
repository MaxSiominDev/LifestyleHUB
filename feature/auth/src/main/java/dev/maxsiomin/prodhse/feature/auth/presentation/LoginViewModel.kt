package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.prodhse.core.UiText
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidatePassword
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidateUsername
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val validateUsername: ValidateUsername = ValidateUsername(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
) : ViewModel() {

    data class State(
        val username: String = "",
        val usernameError: UiText? = null,
        val password: String = "",
        val passwordError: UiText? = null,
        val showForgotPasswordDialog: Boolean = false,
    )

    var state by mutableStateOf(State())
        private set

    sealed class UiEvent {
        data object NavigateToSignupScreen : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()

    sealed class Event {
        data class UsernameChanged(val newValue: String) : Event()
        data class PasswordChanged(val newValue: String) : Event()
        data object LoginClicked : Event()
        data object SignupClicked : Event()
        data object ForgotPasswordClicked : Event()
        data object DismissForgotPasswordDialog : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.UsernameChanged -> state = state.copy(username = event.newValue, usernameError = null)
            is Event.PasswordChanged -> state = state.copy(password = event.newValue, passwordError = null)
            Event.LoginClicked -> onLogin()
            Event.SignupClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.NavigateToSignupScreen)
            }
            Event.ForgotPasswordClicked -> state = state.copy(showForgotPasswordDialog = true)
            Event.DismissForgotPasswordDialog -> state = state.copy(showForgotPasswordDialog = false)
        }
    }

    private fun onLogin() {

    }

}
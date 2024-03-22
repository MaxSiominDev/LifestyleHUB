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
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidatePassword
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidateUsername
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val validateUsername: ValidateUsername = ValidateUsername(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
) : ViewModel() {

    data class State(
        val username: String = "",
        val usernameError: UiText? = null,
        val password: String = "",
        val passwordError: UiText? = null,
    )

    var state by mutableStateOf(State())
        private set

    sealed class UiEvent {
        data class Navigate(val navigate: NavController.() -> Unit) : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()

    sealed class Event {
        data class UsernameChanged(val newValue: String) : Event()
        data class PasswordChanged(val newValue: String) : Event()
        data object LoginClicked : Event()
        data object SignupClicked : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.UsernameChanged -> state = state.copy(username = event.newValue)
            is Event.PasswordChanged -> state = state.copy(password = event.newValue)
            Event.LoginClicked -> navigateToLoginScreen()
            Event.SignupClicked -> onSignup()
        }
    }

    private fun navigateToLoginScreen() {
        viewModelScope.launch {
            _eventsFlow.send(UiEvent.Navigate {
                navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.AuthScreen.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
    }

    private fun onSignup() {
        val validateUsername = validateUsername.execute(state.username)
        val validatePassword = validatePassword.execute(state.password)

        val hasError = listOf(validateUsername, validatePassword).any {
            it.successful.not()
        }

        if (hasError) {
            state = state.copy(
                usernameError = validateUsername.errorMessage,
                passwordError = validatePassword.errorMessage
            )
            return
        }

        viewModelScope.launch {
            val usernameAlreadyExists = authManager.checkIfUsernameExists(state.username)
            if (usernameAlreadyExists) {
                state = state.copy(
                    usernameError = UiText.StringResource(R.string.account)
                )
                return@launch
            }

        }
    }

}
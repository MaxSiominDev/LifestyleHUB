package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.LoginInfo
import dev.maxsiomin.authlib.domain.LoginStatus
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.domain.resource.errorOrNull
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidatePasswordForLogin
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidateUsernameForLogin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val validateUsernameForLogin: ValidateUsernameForLogin,
    private val validatePasswordForLogin: ValidatePasswordForLogin,
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
        data object NavigateToProfileScreen : UiEvent()
        data class ShowError(val message: UiText) : UiEvent()
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
            is Event.UsernameChanged -> state =
                state.copy(username = event.newValue, usernameError = null)

            is Event.PasswordChanged -> state =
                state.copy(password = event.newValue, passwordError = null)

            Event.LoginClicked -> onLogin()
            Event.SignupClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.NavigateToSignupScreen)
            }

            Event.ForgotPasswordClicked -> state = state.copy(showForgotPasswordDialog = true)
            Event.DismissForgotPasswordDialog -> state =
                state.copy(showForgotPasswordDialog = false)
        }
    }

    private fun onLogin() {
        val username = state.username.trim()
        val password = state.password.trim()
        val validateUsername =
            validateUsernameForLogin.execute(username)
        val validatePassword =
            validatePasswordForLogin.execute(password)

        val hasError =
            listOf(validateUsername, validatePassword).any { it !is Resource.Success }

        val usernameError: UiText? = when (validateUsername.errorOrNull()) {
            null -> null
            ValidateUsernameForLogin.UsernameForLoginError.IsBlank -> {
                UiText.StringResource(R.string.blank_username)
            }
        }

        val passwordError: UiText? = when (validatePassword.errorOrNull()) {
            null -> null
            ValidatePasswordForLogin.PasswordForLoginError.IsBlank -> {
                UiText.StringResource(R.string.blank_password)
            }
        }

        if (hasError) {
            state = state.copy(
                usernameError = usernameError,
                passwordError = passwordError,
            )
            return
        }

        viewModelScope.launch {
            val loginInfo = LoginInfo(username = username, password = password)
            val loginStatus = authManager.loginWithUsernameAndPassword(loginInfo)
            when (loginStatus) {

                LoginStatus.Success -> {
                    _eventsFlow.send(UiEvent.NavigateToProfileScreen)
                }

                is LoginStatus.Failure -> {
                    _eventsFlow.send(
                        UiEvent.ShowError(
                            UiText.DynamicString(loginStatus.reason)
                        )
                    )
                }

            }
        }
    }

}
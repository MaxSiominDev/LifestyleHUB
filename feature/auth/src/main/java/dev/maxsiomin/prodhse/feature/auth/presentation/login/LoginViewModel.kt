package dev.maxsiomin.prodhse.feature.auth.presentation.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.model.LoginInfo
import dev.maxsiomin.authlib.domain.LoginStatus
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.domain.resource.errorOrNull
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidatePasswordForLoginUseCase
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidateUsernameForLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val validateUsernameUseCase: ValidateUsernameForLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordForLoginUseCase,
) : StatefulViewModel<LoginViewModel.State, LoginViewModel.Effect, LoginViewModel.Event>() {

    data class State(
        val username: String = "",
        val usernameError: UiText? = null,
        val password: String = "",
        val passwordError: UiText? = null,
        val showForgotPasswordDialog: Boolean = false,
    )

    override val _state = MutableStateFlow(State())

    sealed class Effect {
        data object NavigateToSignupScreen : Effect()
        data object NavigateToProfileScreen : Effect()
        data class ShowMessage(val message: UiText) : Effect()
    }

    sealed class Event {
        data class UsernameChanged(val newValue: String) : Event()
        data class PasswordChanged(val newValue: String) : Event()
        data object LoginClicked : Event()
        data object SignupClicked : Event()
        data object ForgotPasswordClicked : Event()
        data object DismissForgotPasswordDialog : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.UsernameChanged -> _state.update {
                it.copy(username = event.newValue, usernameError = null)
            }

            is Event.PasswordChanged -> _state.update {
                it.copy(password = event.newValue, passwordError = null)
            }

            Event.LoginClicked -> onLogin()

            Event.SignupClicked -> onEffect(Effect.NavigateToSignupScreen)

            Event.ForgotPasswordClicked -> _state.update {
                it.copy(showForgotPasswordDialog = true)
            }

            Event.DismissForgotPasswordDialog -> _state.update {
                it.copy(showForgotPasswordDialog = false)
            }
        }
    }

    private fun onLogin() {
        val username = state.value.username.trim()
        val password = state.value.password.trim()
        val validateUsername =
            validateUsernameUseCase.execute(username)
        val validatePassword =
            validatePasswordUseCase.execute(password)

        val hasError =
            listOf(validateUsername, validatePassword).any { it !is Resource.Success }

        val usernameError: UiText? = when (validateUsername.errorOrNull()) {
            null -> null
            ValidateUsernameForLoginUseCase.UsernameForLoginError.IsBlank -> {
                UiText.StringResource(R.string.blank_username)
            }
        }

        val passwordError: UiText? = when (validatePassword.errorOrNull()) {
            null -> null
            ValidatePasswordForLoginUseCase.PasswordForLoginError.IsBlank -> {
                UiText.StringResource(R.string.blank_password)
            }
        }

        if (hasError) {
            _state.update {
                it.copy(
                    usernameError = usernameError,
                    passwordError = passwordError,
                )
            }
            return
        }

        viewModelScope.launch {
            val loginInfo = LoginInfo(username = username, password = password)
            val loginStatus = authManager.loginWithUsernameAndPassword(loginInfo)
            when (loginStatus) {

                LoginStatus.Success -> {
                    onEffect(Effect.NavigateToProfileScreen)
                }

                is LoginStatus.Failure -> {
                    onEffect(
                        Effect.ShowMessage(
                            UiText.DynamicString(loginStatus.reason)
                        )
                    )
                }

            }
        }
    }

}
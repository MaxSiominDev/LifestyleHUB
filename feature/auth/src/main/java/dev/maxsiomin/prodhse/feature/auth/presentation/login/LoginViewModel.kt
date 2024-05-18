package dev.maxsiomin.prodhse.feature.auth.presentation.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.domain.resource.errorOrNull
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.util.TextFieldState
import dev.maxsiomin.common.util.updateError
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.AuthError
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth.LoginWithUsernameAndPasswordUseCase
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation.ValidatePasswordForLoginUseCase
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.validation.ValidateUsernameForLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loginWithUsernameAndPasswordUseCase: LoginWithUsernameAndPasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameForLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordForLoginUseCase,
) : StatefulViewModel<LoginViewModel.State, LoginViewModel.Effect, LoginViewModel.Event>() {

    data class State(
        val usernameState: TextFieldState = TextFieldState.new(),
        val passwordState: TextFieldState = TextFieldState.new(),
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
                it.copy(usernameState = TextFieldState.new(text = event.newValue))
            }

            is Event.PasswordChanged -> _state.update {
                it.copy(passwordState = TextFieldState.new(text = event.newValue))
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
        val state = _state.value
        val username = state.usernameState.text.trim()
        val password = state.passwordState.text.trim()
        val validateUsername =
            validateUsernameUseCase(username)
        val validatePassword =
            validatePasswordUseCase(password)

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
                    usernameState = state.usernameState.updateError(usernameError),
                    passwordState = state.passwordState.updateError(passwordError),
                )
            }
            return
        }

        viewModelScope.launch {
            val loginStatus =
                loginWithUsernameAndPasswordUseCase(username = username, password = password)
            when (loginStatus) {

                is Resource.Success -> {
                    onEffect(Effect.NavigateToProfileScreen)
                }

                is Resource.Error -> {
                    val effect: Effect = when (loginStatus.error) {

                        is AuthError.Login.Unknown -> {
                            val errorMessage = (loginStatus.error as AuthError.Login.Unknown).reason
                            Effect.ShowMessage(
                                UiText.DynamicString(errorMessage ?: "Unknown error")
                            )
                        }

                    }
                    onEffect(effect)
                }

            }
        }
    }

}
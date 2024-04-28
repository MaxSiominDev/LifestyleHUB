package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.RegistrationInfo
import dev.maxsiomin.authlib.domain.RegistrationStatus
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.domain.resource.errorOrNull
import dev.maxsiomin.common.presentation.asUiText
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.util.TextFieldState
import dev.maxsiomin.common.util.updateError
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomUserRepository
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidatePasswordForSignupUseCase
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidateUsernameForSignupUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repo: RandomUserRepository,
    private val authManager: AuthManager,
    private val validateUsernameUseCase: ValidateUsernameForSignupUseCase,
    private val validatePasswordUseCase: ValidatePasswordForSignupUseCase,
) : ViewModel() {

    data class State(
        val usernameState: TextFieldState = TextFieldState(),
        val password: String = "",
        val passwordError: UiText? = null,
        val showFireworksAnimation: Boolean = false,
    )

    var state by mutableStateOf(State())
        private set

    sealed class UiEvent {
        data object NavigateToLoginScreen : UiEvent()
        data object NavigateToSuccessfulRegistrationScreen : UiEvent()
        data class ShowMessage(val message: UiText) : UiEvent()
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
            is Event.UsernameChanged -> {

                // Easter egg for the person I love the most
                // Just show fireworks animation if username is "rokymiel"
                val showFireworksAnimation = event.newValue.lowercase().trim() == MY_BELOVED_ROKYMIEL

                state = state.copy(
                    usernameState = TextFieldState(event.newValue),
                    showFireworksAnimation = showFireworksAnimation
                )
            }

            is Event.PasswordChanged -> state = state.copy(password = event.newValue, passwordError = null)

            Event.LoginClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.NavigateToLoginScreen)
            }

            Event.SignupClicked -> onSignup()
        }
    }

    private fun onSignup() {
        val username = state.usernameState.text.trim()
        val password = state.password.trim()
        val validateUsername =
            validateUsernameUseCase.execute(username)
        val validatePassword =
            validatePasswordUseCase.execute(password)

        val hasError =
            listOf(validateUsername, validatePassword).any { it !is Resource.Success }

        val usernameError: UiText? = when (validateUsername.errorOrNull()) {
            null -> null
            ValidateUsernameForSignupUseCase.UsernameForSignupError.InvalidLength -> {
                UiText.StringResource(
                    R.string.username_length_violated,
                    ValidateUsernameForSignupUseCase.MIN_USERNAME_LENGTH,
                    ValidateUsernameForSignupUseCase.MAX_USERNAME_LENGTH,
                )
            }
        }

        val passwordError: UiText? = when (validatePassword.errorOrNull()) {
            null -> null
            ValidatePasswordForSignupUseCase.PasswordForSignupError.InvalidLength -> {
                UiText.StringResource(
                    R.string.password_length_violated,
                    ValidatePasswordForSignupUseCase.MIN_PASSWORD_LENGTH,
                    ValidatePasswordForSignupUseCase.MAX_PASSWORD_LENGTH,
                )
            }
            ValidatePasswordForSignupUseCase.PasswordForSignupError.TooWeak -> {
                UiText.StringResource(
                    R.string.password_must_consist_of_letters_and_digits
                )
            }
        }

        if (hasError) {
            state = state.copy(
                usernameState = state.usernameState.updateError(usernameError),
                passwordError = passwordError,
            )
            return
        }

        viewModelScope.launch {
            val usernameAlreadyExists = authManager.checkIfUsernameExists(username)
            if (usernameAlreadyExists) {
                val error = UiText.StringResource(R.string.username_already_exists)
                state = state.copy(
                    usernameState = state.usernameState.updateError(error)
                )
                return@launch
            }

            repo.getRandomUser().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        onRegistrationError(resource.error.asUiText())
                    }

                    is Resource.Success -> {
                        val data = resource.data

                        val registrationStatus = authManager.registerUser(
                            RegistrationInfo(
                                username = username,
                                password = password,
                                avatarUrl = data.avatarUrl,
                                fullName = data.fullName,
                            )
                        )

                        when (registrationStatus) {
                            is RegistrationStatus.Failure -> {
                                onRegistrationError(UiText.DynamicString(registrationStatus.reason))
                            }

                            is RegistrationStatus.Success -> {
                                onRegistrationSuccess()
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun onRegistrationError(message: UiText) {
        _eventsFlow.send(
            UiEvent.ShowMessage(message)
        )
    }

    private suspend fun onRegistrationSuccess() {
        _eventsFlow.send(
            UiEvent.NavigateToSuccessfulRegistrationScreen
        )
    }

    companion object {
        private const val MY_BELOVED_ROKYMIEL = "rokymiel"
    }

}
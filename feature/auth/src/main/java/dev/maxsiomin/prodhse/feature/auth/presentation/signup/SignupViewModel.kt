package dev.maxsiomin.prodhse.feature.auth.presentation.signup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.RegistrationStatus
import dev.maxsiomin.authlib.domain.model.RegistrationInfo
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.domain.resource.errorOrNull
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.asUiText
import dev.maxsiomin.common.util.TextFieldState
import dev.maxsiomin.common.util.updateError
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomUserRepository
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidatePasswordForSignupUseCase
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidateUsernameForSignupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repo: RandomUserRepository,
    private val authManager: AuthManager,
    private val validateUsernameUseCase: ValidateUsernameForSignupUseCase,
    private val validatePasswordUseCase: ValidatePasswordForSignupUseCase,
) : StatefulViewModel<SignupViewModel.State, SignupViewModel.Effect, SignupViewModel.Event>() {

    data class State(
        val usernameState: TextFieldState = TextFieldState.new(),
        val passwordState: TextFieldState = TextFieldState.new(),
        val showFireworksAnimation: Boolean = false,
    )

    override val _state = MutableStateFlow(State())

    sealed class Effect {
        data object NavigateToLoginScreen : Effect()
        data object NavigateToSuccessfulRegistrationScreen : Effect()
        data class ShowMessage(val message: UiText) : Effect()
    }


    sealed class Event {
        data class UsernameChanged(val newValue: String) : Event()
        data class PasswordChanged(val newValue: String) : Event()
        data object LoginClicked : Event()
        data object SignupClicked : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.UsernameChanged -> {

                // Easter egg for the person I love the most
                // Just show fireworks animation if username is "rokymiel"
                val showFireworksAnimation =
                    event.newValue.lowercase().trim() == MY_BELOVED_ROKYMIEL

                _state.update {
                    it.copy(
                        usernameState = TextFieldState.new(text = event.newValue),
                        showFireworksAnimation = showFireworksAnimation
                    )
                }
            }

            is Event.PasswordChanged -> _state.update {
                it.copy(passwordState = TextFieldState.new(text = event.newValue))
            }

            Event.LoginClicked -> onEffect(Effect.NavigateToLoginScreen)

            Event.SignupClicked -> onSignup()
        }
    }

    private fun onSignup() {
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
            _state.update {
                it.copy(
                    usernameState = state.usernameState.updateError(usernameError),
                    passwordState = state.passwordState.updateError(passwordError),
                )
            }
            return
        }

        viewModelScope.launch {
            val usernameAlreadyExists = authManager.checkIfUsernameExists(username)
            if (usernameAlreadyExists) {
                val error = UiText.StringResource(R.string.username_already_exists)
                _state.update {
                    it.copy(
                        usernameState = state.usernameState.updateError(error)
                    )
                }
                return@launch
            }

            val randomUserResource = repo.getRandomUser()
            when (randomUserResource) {
                is Resource.Error -> {
                    onRegistrationError(randomUserResource.error.asUiText())
                }

                is Resource.Success -> {
                    val data = randomUserResource.data

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

    private fun onRegistrationError(message: UiText) {
        onEffect(Effect.ShowMessage(message))
    }

    private fun onRegistrationSuccess() {
        onEffect(Effect.NavigateToSuccessfulRegistrationScreen)
    }

    companion object {
        private const val MY_BELOVED_ROKYMIEL = "rokymiel"
    }

}
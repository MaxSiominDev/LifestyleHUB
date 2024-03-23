package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.RegistrationInfo
import dev.maxsiomin.authlib.domain.RegistrationStatus
import dev.maxsiomin.prodhse.core.Resource
import dev.maxsiomin.prodhse.core.UiText
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomUserRepository
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidatePassword
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.ValidateUsername
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repo: RandomUserRepository,
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
        data object NavigateToLoginScreen : UiEvent()
        data object NavigateToSuccessfulRegistrationScreen : UiEvent()
        data class SignupError(val reason: UiText) : UiEvent()
        data class ShowToast(val message: UiText) : UiEvent()
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
                if (event.newValue.trim() == MY_BELOVED_ROKYMIEL) {
                    viewModelScope.launch {
                        _eventsFlow.send(
                            UiEvent.ShowToast(
                                UiText.StringResource(R.string.hello_rokymiel)
                            )
                        )
                    }
                }

                state = state.copy(username = event.newValue, usernameError = null)
            }

            is Event.PasswordChanged -> state =
                state.copy(password = event.newValue, passwordError = null)

            Event.LoginClicked -> navigateToLoginScreen()
            Event.SignupClicked -> onSignup()
        }
    }

    private fun navigateToLoginScreen() {
        viewModelScope.launch {
            _eventsFlow.send(UiEvent.NavigateToLoginScreen)
        }
    }

    private fun onSignup() {
        val username = state.username
        val password = state.password
        val validateUsername = validateUsername.execute(username)
        val validatePassword = validatePassword.execute(password)

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
            val usernameAlreadyExists = authManager.checkIfUsernameExists(username)
            if (usernameAlreadyExists) {
                state = state.copy(
                    usernameError = UiText.StringResource(R.string.username_already_exists)
                )
                return@launch
            }

            repo.getRandomUser().collect { resource ->
                when (resource) {
                    is Resource.Loading -> Unit
                    is Resource.Error -> {
                        onRegistrationError(resource.exception?.localizedMessage ?: "Unknown error")
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
                                onRegistrationError(registrationStatus.reason)
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

    private suspend fun onRegistrationError(message: String) {
        _eventsFlow.send(
            UiEvent.SignupError(
                UiText.DynamicString(message)
            )
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
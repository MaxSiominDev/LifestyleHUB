package dev.maxsiomin.prodhse.feature.auth.presentation.auth

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.presentation.StatefulViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() :
    StatefulViewModel<Nothing, AuthViewModel.Effect, AuthViewModel.Event>() {

    override val _state: MutableStateFlow<Nothing>
        get() = throw IllegalArgumentException("State is just a placeholder here")

    sealed class Effect {
        data object NavigateToLoginScreen : Effect()
        data object NavigateToSignupScreen : Effect()
    }


    sealed class Event {
        data object LoginClicked : Event()
        data object SignupClicked : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            Event.LoginClicked -> viewModelScope.launch {
                onEffect(Effect.NavigateToLoginScreen)
            }

            Event.SignupClicked -> viewModelScope.launch {
                onEffect(Effect.NavigateToSignupScreen)
            }
        }
    }

}
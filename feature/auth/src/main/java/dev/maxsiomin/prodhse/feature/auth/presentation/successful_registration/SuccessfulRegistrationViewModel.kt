package dev.maxsiomin.prodhse.feature.auth.presentation.successful_registration

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.presentation.StatefulViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
internal class SuccessfulRegistrationViewModel @Inject constructor() :
    StatefulViewModel<Nothing, SuccessfulRegistrationViewModel.Effect, SuccessfulRegistrationViewModel.Event>() {

    override val _state: MutableStateFlow<Nothing>
        get() = throw IllegalArgumentException("State is just a placeholder here")

    sealed class Effect {
        data object NavigateToLoginScreen : Effect()
    }


    sealed class Event {
        data object LoginClicked : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            Event.LoginClicked -> onEffect(Effect.NavigateToLoginScreen)
        }
    }

}
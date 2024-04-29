package dev.maxsiomin.prodhse.feature.auth.presentation.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.presentation.StatefulViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val authManager: AuthManager,
) : StatefulViewModel<ProfileViewModel.State, Nothing, ProfileViewModel.Event>() {

    data class State(
        val authStatus: AuthStatus = AuthStatus.Loading,
    )

    override val _state = MutableStateFlow(State())


    sealed class Event {
        data object LogoutClicked : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            Event.LogoutClicked -> viewModelScope.launch {
                authManager.logout()
            }
        }
    }

    init {
        val status = authManager.authStatus.value
        _state.update {
            it.copy(authStatus = status)
        }
        viewModelScope.launch {
            authManager.authStatus.collect {
                _state.update {  oldState ->
                    oldState.copy(authStatus = it)
                }
            }
        }
    }


}
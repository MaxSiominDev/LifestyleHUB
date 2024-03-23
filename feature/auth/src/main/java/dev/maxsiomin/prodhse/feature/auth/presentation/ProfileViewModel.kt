package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.AuthStatus
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val authManager: AuthManager,
): ViewModel() {

    data class State(
        val authStatus: AuthStatus = AuthStatus.Loading
    )

    var state by mutableStateOf(State())
        private set

    sealed class Event {
        data object LogoutClicked : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.LogoutClicked -> viewModelScope.launch {
                authManager.logout()
            }
        }
    }

    init {
        val status = authManager.authStatus.value
        state = state.copy(authStatus = status)
        viewModelScope.launch {
            authManager.authStatus.collect {
                state = state.copy(authStatus = it)
            }
        }
    }

}
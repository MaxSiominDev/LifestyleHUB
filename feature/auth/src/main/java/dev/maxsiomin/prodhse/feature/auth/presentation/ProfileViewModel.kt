package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.RandomActivityModel
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomActivityRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val randomActivityRepo: RandomActivityRepository,
): ViewModel() {

    data class State(
        val authStatus: AuthStatus = AuthStatus.Loading,
        val randomActivity: RandomActivityModel? = null,
        val showRandomActivityDialog: Boolean = false,
    )

    var state by mutableStateOf(State())
        private set

    sealed class Event {
        data object LogoutClicked : Event()
        data object BoredClicked : Event()
        data object DismissRandomActivityDialog : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.LogoutClicked -> viewModelScope.launch {
                authManager.logout()
            }
            Event.BoredClicked -> state = state.copy(showRandomActivityDialog = true)
            Event.DismissRandomActivityDialog -> state = state.copy(showRandomActivityDialog = false)
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
        loadRandomActivity()
    }

    private fun loadRandomActivity() {
        viewModelScope.launch {
            randomActivityRepo.getRandomActivity().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(randomActivity = resource.data)
                    }
                    else -> Unit
                }
            }
        }
    }

}
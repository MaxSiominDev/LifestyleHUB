package dev.maxsiomin.prodhse.feature.auth.presentation.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.domain.AuthStatus
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth.GetAuthStatusFlowUseCase
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth.GetAuthStatusValueUseCase
import dev.maxsiomin.prodhse.feature.auth.domain.use_case.auth.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getAuthStatusFlowUseCase: GetAuthStatusFlowUseCase,
    private val getAuthStatusValueUseCase: GetAuthStatusValueUseCase,
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
                logoutUseCase()
            }
        }
    }

    init {
        val status = getAuthStatusValueUseCase()
        _state.update {
            it.copy(authStatus = status)
        }
        viewModelScope.launch {
            getAuthStatusFlowUseCase().collect {
                _state.update {  oldState ->
                    oldState.copy(authStatus = it)
                }
            }
        }
    }


}
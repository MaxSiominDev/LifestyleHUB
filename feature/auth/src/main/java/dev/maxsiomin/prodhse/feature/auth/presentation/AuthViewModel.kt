package dev.maxsiomin.prodhse.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authManager: AuthManager) : ViewModel() {

    sealed class UiEvent {
        data class Navigate(val navigate: NavController.() -> Unit) : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()


    sealed class Event {
        data object LoginClicked : Event()
        data object SignupClicked : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.LoginClicked -> {
                viewModelScope.launch {
                    _eventsFlow.send(
                        UiEvent.Navigate(navigate = {
                            navigate(Screen.LoginScreen.route)
                        })
                    )
                }
            }
            Event.SignupClicked -> {
                viewModelScope.launch {
                    _eventsFlow.send(
                        UiEvent.Navigate(navigate = {
                            navigate(Screen.SignupScreen.route)
                        })
                    )
                }
            }
        }
    }

}
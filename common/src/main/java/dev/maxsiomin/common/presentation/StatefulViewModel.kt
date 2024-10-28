package dev.maxsiomin.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class StatefulViewModel<State : Any, Effect : Any, Event : Any> : ViewModel() {

    protected abstract val _state: MutableStateFlow<State>
    val state by lazy {
        _state.asStateFlow()
    }

    private val _effectChannel = Channel<Effect>()
    val effectFlow = _effectChannel.receiveAsFlow()

    protected fun onEffect(effect: Effect) {
        viewModelScope.launch(Dispatchers.Main) {
            _effectChannel.send(effect)
        }
    }

    abstract fun onEvent(event: Event)
}
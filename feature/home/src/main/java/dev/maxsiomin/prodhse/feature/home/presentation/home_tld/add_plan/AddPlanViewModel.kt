package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.add_plan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.extensions.requireArg
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.asErrorUiText
import dev.maxsiomin.common.util.DateConverters
import dev.maxsiomin.prodhse.core.util.DateFormatter
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class AddPlanViewModel @Inject constructor(
    private val plansRepo: PlansRepository,
    private val placesRepo: PlacesRepository,
    private val dateFormatter: DateFormatter,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val fsqId: String = savedStateHandle.requireArg(Screen.AddPlanScreenArgs.FSQ_ID)

    data class State(
        val placeDetails: PlaceDetails? = null,
        val dateString: String,
        val dateLocalDate: LocalDate,
        val noteTitle: String = "",
        val noteText: String = "",
        val isLoading: Boolean = false,
        val isError: Boolean = false,
    )

    var state by mutableStateOf(
        State(
            dateString = dateFormatter.formatDate(System.currentTimeMillis()),
            dateLocalDate = LocalDate.now(),
        )
    )
        private set


    sealed class UiEvent {
        data object NavigateBack : UiEvent()
        data class ShowMessage(val message: UiText) : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()

    sealed class Event {
        data class NewDateSelected(val newDate: LocalDate) : Event()
        data class NoteTitleChanged(val newValue: String) : Event()
        data class NoteTextChanged(val newValue: String) : Event()
        data object SaveClicked : Event()
        data object Refresh : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.NewDateSelected -> onNewDate(newDate = event.newDate)
            is Event.NoteTitleChanged -> state = state.copy(noteTitle = event.newValue)
            is Event.NoteTextChanged -> state = state.copy(noteText = event.newValue)
            Event.SaveClicked -> onSaveClicked()
            Event.Refresh -> loadPlaceDetails(id = fsqId)
        }
    }

    init {
        loadPlaceDetails(id = fsqId)
    }

    private fun loadPlaceDetails(id: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            placesRepo.getPlaceDetails(id).collect { resource ->
                when (resource) {

                    is Resource.Error -> {
                        state = state.copy(isLoading = false, isError = true)
                        _eventsFlow.send(UiEvent.ShowMessage(resource.asErrorUiText()))
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            placeDetails = resource.data,
                            isError = false,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun onNewDate(newDate: LocalDate) {
        val epochMillis = DateConverters.localDateToEpochMillis(newDate)

        state = state.copy(
            dateString = dateFormatter.formatDate(epochMillis),
            dateLocalDate = newDate,
        )
    }

    private fun onSaveClicked() {
        viewModelScope.launch {
            val state = state
            val fsqId = fsqId
            val name = state.placeDetails?.name ?: return@launch

            val plan = Plan(
                placeFsqId = fsqId,
                noteTitle = state.noteTitle,
                noteText = state.noteText,
                date = DateConverters.localDateToEpochMillis(state.dateLocalDate),
                // Room will create new record
                databaseId = 0,
                dateString = state.dateString,
            )
            plansRepo.addPlan(plan)

            _eventsFlow.send(UiEvent.ShowMessage(UiText.StringResource(R.string.plan_added, name)))
            _eventsFlow.send(UiEvent.NavigateBack)
        }
    }

}
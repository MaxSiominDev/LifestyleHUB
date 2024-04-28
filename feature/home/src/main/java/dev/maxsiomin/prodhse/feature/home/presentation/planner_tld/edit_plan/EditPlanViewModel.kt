package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.edit_plan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.DatabaseError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.extensions.requireArg
import dev.maxsiomin.common.presentation.asErrorUiText
import dev.maxsiomin.prodhse.core.util.DateFormatter
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.util.DateConverters
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class EditPlanViewModel @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val plansRepo: PlansRepository,
    private val placesRepo: PlacesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val planId: Long =
        savedStateHandle.requireArg<String>(Screen.EditPlanScreenArgs.PLAN_ID).toLong()

    data class State(
        val placeDetails: PlaceDetails? = null,
        val dateString: String,
        val originalLocalDate: LocalDate,
        val dateLocalDate: LocalDate,
        val originalNoteTitle: String = "",
        val noteTitle: String = "",
        val originalNoteText: String = "",
        val noteText: String = "",
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isNotSaved: Boolean = false,
    )

    var state by mutableStateOf(
        State(
            dateString = dateFormatter.formatDate(System.currentTimeMillis()),
            dateLocalDate = LocalDate.now(),
            originalLocalDate = LocalDate.now(),
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

    init {
        loadPlan(id = planId)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.NewDateSelected -> {
                onNewDate(newDate = event.newDate)
                checkIfNotSaved()
            }

            is Event.NoteTitleChanged -> {
                state = state.copy(noteTitle = event.newValue)
                checkIfNotSaved()
            }

            is Event.NoteTextChanged -> {
                state = state.copy(noteText = event.newValue)
                checkIfNotSaved()
            }

            Event.SaveClicked -> onSaveClicked()

            Event.Refresh -> loadPlan(id = planId)
        }
    }

    private fun checkIfNotSaved() {
        val titleIsSaved = state.originalNoteTitle == state.noteTitle
        val textIsSaved = state.originalNoteText == state.noteText
        val dateIsSaved = state.originalLocalDate == state.dateLocalDate
        val isSaved = titleIsSaved && textIsSaved && dateIsSaved
        state = state.copy(isNotSaved = isSaved.not())
    }

    private fun loadPlan(id: Long) {
        viewModelScope.launch {
            val plan = when (val planResource = plansRepo.getPlanById(id)) {
                is Resource.Error -> {
                    val message: UiText = when (planResource.error) {
                        DatabaseError.NotFound -> UiText.StringResource(R.string.plan_not_found)
                    }
                    _eventsFlow.send(UiEvent.ShowMessage(message))
                    return@launch
                }

                is Resource.Success -> planResource.data
            }
            state = state.copy(
                originalNoteTitle = plan.noteTitle,
                noteTitle = plan.noteTitle,
                originalNoteText = plan.noteText,
                noteText = plan.noteText,
                dateString = plan.dateString,
                dateLocalDate = DateConverters.epochMillisToLocalDate(plan.date)
            )
            loadPlaceDetails(plan.placeFsqId)
        }
    }

    private fun loadPlaceDetails(id: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            placesRepo.getPlaceDetails(id).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        state = state.copy(isLoading = false, isError = true)
                        _eventsFlow.send(
                            UiEvent.ShowMessage(resource.asErrorUiText())
                        )
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
            val planId = planId ?: return@launch

            val plan = when (val planResource = plansRepo.getPlanById(planId)) {
                is Resource.Error -> {
                    val message: UiText = when (planResource.error) {
                        DatabaseError.NotFound -> UiText.StringResource(R.string.plan_not_found)
                    }
                    _eventsFlow.send(UiEvent.ShowMessage(message))
                    return@launch
                }

                is Resource.Success -> planResource.data
            }

            val millis = DateConverters.localDateToEpochMillis(state.dateLocalDate)

            val newPlan = plan.copy(
                noteTitle = state.noteTitle,
                noteText = state.noteText,
                date = millis,
                dateString = dateFormatter.formatDate(millis),
            )
            plansRepo.editPlan(newPlan)

            _eventsFlow.send(UiEvent.ShowMessage(UiText.StringResource(R.string.plan_updated)))
            _eventsFlow.send(UiEvent.NavigateBack)
        }
    }

}
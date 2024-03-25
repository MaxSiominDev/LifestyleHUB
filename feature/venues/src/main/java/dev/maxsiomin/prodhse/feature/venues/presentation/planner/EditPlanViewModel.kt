package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.util.DateFormatter
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.core.util.UiText
import dev.maxsiomin.prodhse.feature.venues.R
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlansRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
internal class EditPlanViewModel @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val plansRepo: PlansRepository,
    private val placesRepo: PlacesRepository,
) : ViewModel() {

    private var planId: Long? = null

    data class State(
        val placeDetails: PlaceDetailsModel? = null,
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
        data class ShowSnackbar(val message: UiText) : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()

    sealed class Event {
        data class PassPlanId(val planId: Long) : Event()
        data class NewDateSelected(val newDate: LocalDate) : Event()
        data class NoteTitleChanged(val newValue: String) : Event()
        data class NoteTextChanged(val newValue: String) : Event()
        data object SaveClicked : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.PassPlanId -> loadPlan(event.planId)
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
        planId = id
        viewModelScope.launch {
            val plan = plansRepo.getPlanById(id)
            if (plan == null) {
                _eventsFlow.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.plan_not_found)))
                return@launch
            }
            state = state.copy(
                originalNoteTitle = plan.noteTitle,
                noteTitle = plan.noteTitle,
                originalNoteText = plan.noteText,
                noteText = plan.noteText,
                dateString = plan.dateString,
                dateLocalDate = Instant.ofEpochMilli(plan.date).toLocalDate()
            )
            loadPlaceDetails(plan.placeFsqId)
        }
    }

    private fun loadPlaceDetails(id: String) {
        viewModelScope.launch {
            placesRepo.getPlaceDetails(id).collect { resource ->
                when (resource) {

                    is Resource.Loading -> {
                        state = state.copy(isLoading = resource.isLoading)
                    }

                    is Resource.Error -> {
                        state = state.copy(isLoading = false, isError = true)
                        _eventsFlow.send(
                            UiEvent.ShowSnackbar(
                                UiText.DynamicString(
                                    resource.exception.localizedMessage ?: "Error"
                                )
                            )
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(placeDetails = resource.data, isError = false)
                    }
                }
            }
        }
    }

    private fun onNewDate(newDate: LocalDate) {
        val epochMillis = newDate.toEpochMillis()

        state = state.copy(
            dateString = dateFormatter.formatDate(epochMillis),
            dateLocalDate = newDate,
        )
    }

    private fun onSaveClicked() {
        viewModelScope.launch {
            val planId = planId ?: return@launch

            val plan = plansRepo.getPlanById(planId)
            if (plan == null) {
                _eventsFlow.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.plan_not_found)))
                return@launch
            }

            val millis = state.dateLocalDate.toEpochMillis()

            val newPlan = plan.copy(
                noteTitle = state.noteTitle,
                noteText = state.noteText,
                date = millis,
                dateString = dateFormatter.formatDate(millis),
            )
            plansRepo.editPlan(newPlan)

            _eventsFlow.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.plan_updated)))
            _eventsFlow.send(UiEvent.NavigateBack)
        }
    }

    private fun LocalDate.toEpochMillis(): Long {
        val zoneId: ZoneId = ZoneId.systemDefault()
        val startOfDay = this.atStartOfDay(zoneId)
        val epochMillis = startOfDay.toInstant().toEpochMilli()
        return epochMillis
    }

    private fun Instant.toLocalDate(): LocalDate {
        val zoneId = ZoneId.systemDefault()
        return this
            .atZone(zoneId)
            .toLocalDate()
    }

}
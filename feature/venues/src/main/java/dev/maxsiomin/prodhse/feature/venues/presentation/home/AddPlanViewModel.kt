package dev.maxsiomin.prodhse.feature.venues.presentation.home

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
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
internal class AddPlanViewModel @Inject constructor(
    private val plansRepo: PlansRepository,
    private val placesRepo: PlacesRepository,
    private val dateFormatter: DateFormatter,
) : ViewModel() {

    private var fsqId: String? = null

    data class State(
        val placeDetails: PlaceDetailsModel? = null,
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
        data class ShowSnackbar(val message: UiText) : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()

    sealed class Event {
        data class PassPlaceId(val fsqId: String) : Event()
        data class NewDateSelected(val newDate: LocalDate) : Event()
        data class NoteTitleChanged(val newValue: String) : Event()
        data class NoteTextChanged(val newValue: String) : Event()
        data object SaveClicked : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.PassPlaceId -> loadPlaceDetails(id = event.fsqId)
            is Event.NewDateSelected -> onNewDate(newDate = event.newDate)
            is Event.NoteTitleChanged -> state = state.copy(noteTitle = event.newValue)
            is Event.NoteTextChanged -> state = state.copy(noteText = event.newValue)
            Event.SaveClicked -> onSaveClicked()
        }
    }

    private fun loadPlaceDetails(id: String) {
        fsqId = id
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
            val fsqId = fsqId
            val name = state.placeDetails?.name
            if (fsqId == null || name == null) return@launch

            val plan = PlanModel(
                placeFsqId = fsqId,
                noteTitle = state.noteTitle,
                noteText = state.noteText,
                date = state.dateLocalDate.toEpochMillis(),
                // Room will create new record
                databaseId = 0,
                dateString = state.dateString,
            )
            plansRepo.addPlan(plan)

            _eventsFlow.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.plan_added, name)))
            _eventsFlow.send(UiEvent.NavigateBack)
        }
    }

    private fun LocalDate.toEpochMillis(): Long {
        val zoneId: ZoneId = ZoneId.systemDefault()
        val startOfDay = this.atStartOfDay(zoneId)
        val epochMillis = startOfDay.toInstant().toEpochMilli()
        return epochMillis
    }

}
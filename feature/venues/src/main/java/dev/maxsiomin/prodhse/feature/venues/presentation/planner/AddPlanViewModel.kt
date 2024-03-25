package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.util.DateFormatter
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlansRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class AddPlanViewModel @Inject constructor(
    private val plansRepo: PlansRepository,
    private val placesRepo: PlacesRepository,
    private val dateFormatter: DateFormatter,
) : ViewModel() {

    private val epochMillis = System.currentTimeMillis()

    data class State(
        val placeDetails: PlaceDetailsModel? = null,
        val dateString: String,
        val dateLocalDate: LocalDate,
        val noteTitle: String = "",
        val noteText: String = "",
    )

    var state by mutableStateOf(
        State(
            dateString = dateFormatter.formatDate(System.currentTimeMillis()),
            dateLocalDate = LocalDate.now(),
        )
    )
        private set

    sealed class Event {
        data class PassPlaceId(val fsqId: String) : Event()
        data class NewDateSelected(val newDate: LocalDate) : Event()
        data class NoteTitleChanged(val newValue: String) : Event()
        data class NoteTextChanged(val newValue: String) : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.PassPlaceId -> loadPlaceDetails(id = event.fsqId)
            is Event.NewDateSelected -> onNewDate(newDate = event.newDate)
            is Event.NoteTitleChanged -> state = state.copy(noteTitle = event.newValue)
            is Event.NoteTextChanged -> state = state.copy(noteText = event.newValue)
        }
    }

    private fun loadPlaceDetails(id: String) {
        viewModelScope.launch {
            placesRepo.getPlaceDetails(id).collect { resource ->
                when (resource) {
                    is Resource.Loading -> Unit
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        state = state.copy(placeDetails = resource.data)
                    }
                }
            }
        }
    }

    private fun onNewDate(newDate: LocalDate) {
        val zoneId: ZoneId = ZoneId.systemDefault()
        val startOfDay = newDate.atStartOfDay(zoneId)
        val epochMillis = startOfDay.toInstant().toEpochMilli()
        state = state.copy(
            dateString = dateFormatter.formatDate(epochMillis),
            dateLocalDate = newDate,
        )
    }

}
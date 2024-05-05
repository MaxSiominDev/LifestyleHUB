package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.add_plan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.extensions.requireArg
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.asErrorUiText
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.model.Plan
import dev.maxsiomin.prodhse.feature.home.domain.use_case.date.GetInitialStringDateUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.places.GetPlaceDetailsByIdUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.date.LocalDateToStringDateUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.SavePlanUseCase
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class AddPlanViewModel @Inject constructor(
    private val savePlanUseCase: SavePlanUseCase,
    private val getInitialStringDateUseCase: GetInitialStringDateUseCase,
    private val getPlaceDetailsByIdUseCase: GetPlaceDetailsByIdUseCase,
    private val localDateToStringDateUseCase: LocalDateToStringDateUseCase,
    savedStateHandle: SavedStateHandle,
) : StatefulViewModel<AddPlanViewModel.State, AddPlanViewModel.Effect, AddPlanViewModel.Event>() {

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

    override val _state = MutableStateFlow(
        State(
            dateString = getInitialStringDateUseCase(),
            dateLocalDate = LocalDate.now(),
        )
    )


    sealed class Effect {
        data object NavigateBack : Effect()
        data class ShowMessage(val message: UiText) : Effect()
    }


    sealed class Event {
        data class NewDateSelected(val newDate: LocalDate) : Event()
        data class NoteTitleChanged(val newValue: String) : Event()
        data class NoteTextChanged(val newValue: String) : Event()
        data object SaveClicked : Event()
        data object Refresh : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.NewDateSelected -> onNewDate(newDate = event.newDate)
            is Event.NoteTitleChanged -> _state.update {
                it.copy(noteTitle = event.newValue)
            }

            is Event.NoteTextChanged -> _state.update {
                it.copy(noteText = event.newValue)
            }

            Event.SaveClicked -> onSaveClicked()
            Event.Refresh -> loadPlaceDetails(fsqId = fsqId)
        }
    }

    init {
        loadPlaceDetails(fsqId = fsqId)
    }

    private fun loadPlaceDetails(fsqId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val placeDetailsResource = getPlaceDetailsByIdUseCase(fsqId)
            when (placeDetailsResource) {

                is Resource.Error -> {
                    _state.update {
                        it.copy(isLoading = false, isError = true)
                    }
                    onEffect(Effect.ShowMessage(placeDetailsResource.asErrorUiText()))
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            placeDetails = placeDetailsResource.data,
                            isError = false,
                            isLoading = false
                        )
                    }
                }

            }
        }
    }

    private fun onNewDate(newDate: LocalDate) {
        _state.update {
            it.copy(
                dateString = localDateToStringDateUseCase(localDate = newDate),
                dateLocalDate = newDate,
            )
        }
    }

    private fun onSaveClicked() {
        viewModelScope.launch {
            val state = _state.value
            val name = state.placeDetails?.name ?: return@launch

            val plan = Plan(
                placeFsqId = fsqId,
                noteTitle = state.noteTitle,
                noteText = state.noteText,
                date = state.dateLocalDate,
                dateString = state.dateString,
                databaseId = null,
            )
            savePlanUseCase(plan)

            onEffect(Effect.ShowMessage(UiText.StringResource(R.string.plan_added, name)))
            onEffect(Effect.NavigateBack)
        }
    }

}
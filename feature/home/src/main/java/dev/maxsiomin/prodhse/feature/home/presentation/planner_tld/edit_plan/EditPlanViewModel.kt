package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.edit_plan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.LocalError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.extensions.requireArg
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.common.presentation.asErrorUiText
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.use_case.date.GetInitialStringDateUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.GetPlanByIdUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.date.LocalDateToStringDateUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.places.GetPlaceDetailsByIdUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.plans.SavePlanUseCase
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class EditPlanViewModel @Inject constructor(
    private val getInitialStringDateUseCase: GetInitialStringDateUseCase,
    private val localDateToStringDateUseCase: LocalDateToStringDateUseCase,
    private val getPlanByIdUseCase: GetPlanByIdUseCase,
    private val savePlanUseCase: SavePlanUseCase,
    private val getPlaceDetailsByIdUseCase: GetPlaceDetailsByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : StatefulViewModel<EditPlanViewModel.State, EditPlanViewModel.Effect, EditPlanViewModel.Event>() {

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

    override val _state = MutableStateFlow(
        State(
            dateString = getInitialStringDateUseCase(),
            dateLocalDate = LocalDate.now(),
            originalLocalDate = LocalDate.now(),
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

    init {
        loadPlan(planId = planId)
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.NewDateSelected -> {
                onNewDate(newDate = event.newDate)
                checkIfNotSaved()
            }

            is Event.NoteTitleChanged -> {
                _state.update {
                    it.copy(noteTitle = event.newValue)
                }
                checkIfNotSaved()
            }

            is Event.NoteTextChanged -> {
                _state.update {
                    it.copy(noteText = event.newValue)
                }
                checkIfNotSaved()
            }

            Event.SaveClicked -> onSaveClicked()

            Event.Refresh -> loadPlan(planId = planId)
        }
    }

    private fun checkIfNotSaved() {
        val state = state.value
        val titleIsSaved = state.originalNoteTitle == state.noteTitle
        val textIsSaved = state.originalNoteText == state.noteText
        val dateIsSaved = state.originalLocalDate == state.dateLocalDate
        val isSaved = titleIsSaved && textIsSaved && dateIsSaved
        _state.update {
            it.copy(isNotSaved = isSaved.not())
        }
    }

    private fun loadPlan(planId: Long) {
        viewModelScope.launch {
            val planResource = getPlanByIdUseCase(planId = planId)
            val plan = when (planResource) {
                is Resource.Error -> {
                    val message: UiText = when (planResource.error) {
                        is LocalError.Unknown -> UiText.StringResource(R.string.plan_not_found)
                    }
                    onEffect(Effect.ShowMessage(message))
                    return@launch
                }

                is Resource.Success -> planResource.data
            }
            _state.update {
                it.copy(
                    originalNoteTitle = plan.noteTitle,
                    noteTitle = plan.noteTitle,
                    originalNoteText = plan.noteText,
                    noteText = plan.noteText,
                    dateString = plan.dateString,
                    dateLocalDate = plan.date,
                )
            }
            loadPlaceDetails(plan.placeFsqId)
        }
    }

    private fun loadPlaceDetails(fsqId: String) {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val placeDetailsResource = getPlaceDetailsByIdUseCase(fsqId = fsqId)
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
                dateString = localDateToStringDateUseCase(newDate),
                dateLocalDate = newDate,
            )
        }
    }

    private fun onSaveClicked() {
        viewModelScope.launch {
            val state = state.value

            val planResource = getPlanByIdUseCase.invoke(planId)
            val plan = when (planResource) {
                is Resource.Error -> {
                    onEffect(Effect.ShowMessage(planResource.asErrorUiText()))
                    return@launch
                }

                is Resource.Success -> planResource.data
            }

            val newPlan = plan.copy(
                noteTitle = state.noteTitle,
                noteText = state.noteText,
                date = state.dateLocalDate,
                dateString = localDateToStringDateUseCase(state.dateLocalDate),
            )
            savePlanUseCase(newPlan)

            onEffect(Effect.ShowMessage(UiText.StringResource(R.string.plan_updated)))
            onEffect(Effect.NavigateBack)
        }
    }

}
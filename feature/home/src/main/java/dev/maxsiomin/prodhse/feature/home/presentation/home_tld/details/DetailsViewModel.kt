package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.common.extensions.requireArg
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.asErrorUiText
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.use_case.other.EncodeUrlUseCase
import dev.maxsiomin.prodhse.feature.home.domain.use_case.places.GetPlaceDetailsByIdUseCase
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val getPlaceDetailsByIdUseCase: GetPlaceDetailsByIdUseCase,
    private val encodeUrlUseCase: EncodeUrlUseCase,
    savedStateHandle: SavedStateHandle,
) : StatefulViewModel<DetailsViewModel.State, DetailsViewModel.Effect, DetailsViewModel.Event>() {

    sealed class Effect {
        data class NavigateToPhotoScreen(val url: String) : Effect()
        data class NavigateToAddPlanScreen(val fsqId: String) : Effect()
        data class ShowMessage(val message: UiText) : Effect()
    }

    data class State(
        val placeDetails: PlaceDetails? = null,
    )

    override val _state = MutableStateFlow(State())

    private val fsqId: String = savedStateHandle.requireArg(Screen.DetailsScreenArgs.FSQ_ID)

    init {
        loadPlaceDetails(fsqId = fsqId)
    }


    sealed class Event {
        data class ImageClicked(val url: String) : Event()
        data object IconAddToPlansClicked : Event()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.ImageClicked -> {
                val encodedUrl = encodeUrlUseCase(event.url)
                onEffect(Effect.NavigateToPhotoScreen(encodedUrl))
            }

            is Event.IconAddToPlansClicked -> onEffect(Effect.NavigateToAddPlanScreen(fsqId))
        }
    }

    private fun loadPlaceDetails(fsqId: String) {
        viewModelScope.launch {
            val resource = getPlaceDetailsByIdUseCase(fsqId = fsqId)
            when (resource) {
                is Resource.Error -> {
                    onEffect(Effect.ShowMessage(resource.asErrorUiText()))
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(placeDetails = resource.data)
                    }
                }
            }
        }
    }

}

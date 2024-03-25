package dev.maxsiomin.prodhse.feature.venues.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val repo: PlacesRepository,
) : ViewModel() {

    sealed class UiEvent {
        data class NavigateToPhotoScreen(val url: String) : UiEvent()
        data class NavigateToAddPlanScreen(val fsqId: String) : UiEvent()
    }

    private val _eventsFlow = Channel<UiEvent>()
    val eventsFlow = _eventsFlow.receiveAsFlow()

    data class State(
        val placeDetails: PlaceDetailsModel? = null,
    )

    var state by mutableStateOf(State())
        private set

    sealed class Event {
        data class PassPlaceId(val placeId: String) : Event()
        data class ImageClicked(val url: String) : Event()
        data class IconAddToPlansClicked(val fsqId: String) : Event()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.PassPlaceId -> loadPlaceDetails(event.placeId)
            is Event.ImageClicked -> viewModelScope.launch {
                val encodedUrl = URLEncoder.encode(event.url, "UTF-8")
                _eventsFlow.send(UiEvent.NavigateToPhotoScreen(encodedUrl))
            }
            is Event.IconAddToPlansClicked -> viewModelScope.launch {
                _eventsFlow.send(UiEvent.NavigateToAddPlanScreen(event.fsqId))
            }
        }
    }


    private fun loadPlaceDetails(id: String) {
        viewModelScope.launch {
            repo.getPlaceDetails(id).collect { resource ->
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

}

package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.browse_photo

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.extensions.requireArg
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.prodhse.feature.home.domain.use_case.other.DecodeUrlUseCase
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
internal class BrowsePhotoViewModel @Inject constructor(
    private val decodeUrlUseCase: DecodeUrlUseCase,
    savedStateHandle: SavedStateHandle
) : StatefulViewModel<BrowsePhotoViewModel.State, Nothing, Nothing>() {

    private val imageUrl: String

    init {
        val encodedUrl: String = savedStateHandle.requireArg(Screen.BrowsePhotoScreenArgs.URL)
        imageUrl = decodeUrlUseCase(encodedUrl = encodedUrl)
    }


    data class State(
        val url: String,
    )

    override val _state = MutableStateFlow(
        State(url = imageUrl)
    )

    override fun onEvent(event: Nothing) {

    }
}
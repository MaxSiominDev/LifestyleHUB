package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.fullscreen_photo

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.extensions.requireArg
import dev.maxsiomin.common.presentation.StatefulViewModel
import dev.maxsiomin.prodhse.navdestinations.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class BrowsePhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : StatefulViewModel<BrowsePhotoViewModel.State, Nothing, Nothing>() {

    private val imageUrl: String =
        savedStateHandle.requireArg<String>(Screen.BrowsePhotoScreenArgs.URL).let { encodedUrl ->
            URLDecoder.decode(encodedUrl, "UTF-8")
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
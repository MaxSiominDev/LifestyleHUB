package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.fullscreen_photo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.common.extensions.requireArg
import dev.maxsiomin.prodhse.navdestinations.Screen
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class BrowsePhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val imageUrl: String =
        savedStateHandle.requireArg<String>(Screen.BrowsePhotoScreenArgs.URL).let { encodedUrl ->
            URLDecoder.decode(encodedUrl, "UTF-8")
        }

    data class State(
        val url: String,
    )

    var state by mutableStateOf(
        State(url = imageUrl)
    )
        private set

}
package dev.maxsiomin.prodhse.core.presentation

import dev.maxsiomin.prodhse.core.R
import dev.maxsiomin.prodhse.core.domain.NetworkError
import dev.maxsiomin.prodhse.core.domain.Resource
import dev.maxsiomin.prodhse.core.util.UiText

fun NetworkError.asUiText(): UiText {
    return when (this) {

        NetworkError.Server, NetworkError.InvalidRequest, NetworkError.Redirected, NetworkError.EmptyResponse -> {
            UiText.StringResource(R.string.server_error)
        }

        is NetworkError.Unknown -> {
            message?.let {
                UiText.DynamicString(it)
            } ?: UiText.StringResource(
                R.string.unknown_error,
            )
        }

    }

}

fun Resource.Error<*, NetworkError>.asErrorUiText(): UiText {
    return error.asUiText()
}
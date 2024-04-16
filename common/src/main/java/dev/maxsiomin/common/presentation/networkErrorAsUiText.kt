package dev.maxsiomin.common.presentation

import dev.maxsiomin.common.R
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource

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
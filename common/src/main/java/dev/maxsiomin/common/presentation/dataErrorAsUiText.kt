package dev.maxsiomin.common.presentation

import dev.maxsiomin.common.R
import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.LocalError
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource

fun Resource.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}

fun DataError.asUiText(): UiText {
    return when (this) {
        is LocalError -> this.asUiText()
        is NetworkError -> this.asUiText()
    }
}

private fun LocalError.asUiText(): UiText {
    return when (this) {
        is LocalError.Unknown -> {
            message?.let {
                UiText.DynamicString(it)
            } ?: UiText.StringResource(
                R.string.unknown_error,
            )
        }
    }
}

private fun NetworkError.asUiText(): UiText {
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

package dev.maxsiomin.common.presentation

import dev.maxsiomin.common.R
import dev.maxsiomin.common.domain.LocationError
import dev.maxsiomin.common.domain.NetworkError
import dev.maxsiomin.common.domain.Resource

fun LocationError.asUiText(): UiText {
    return when (this) {

        is LocationError.Unknown -> {
            UiText.StringResource(R.string.unknown_error)
        }

        LocationError.GpsDisabled -> {
            UiText.StringResource(R.string.gps_disabled)
        }

        LocationError.MissingPermission -> {
            UiText.StringResource(R.string.missing_permission)
        }
    }

}

fun Resource.Error<*, LocationError>.asErrorUiText(): UiText {
    return error.asUiText()
}
package dev.maxsiomin.common.presentation

import dev.maxsiomin.common.R
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource

fun LocationError.asUiText(): UiText {
    return when (this) {

        is LocationError.Unknown -> {
            UiText.StringResource(R.string.unknown_location_error)
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
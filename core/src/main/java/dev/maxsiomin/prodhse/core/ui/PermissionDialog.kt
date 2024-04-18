package dev.maxsiomin.prodhse.core.ui

import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.components.PermissionTextProvider
import dev.maxsiomin.prodhse.core.R

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): UiText {
        return if (isPermanentlyDeclined) {
            UiText.StringResource(R.string.location_permission_declined)
        } else {
            UiText.StringResource(R.string.location_permission_rationale)
        }
    }
}

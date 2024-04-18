package dev.maxsiomin.prodhse.core.presentation

import dev.maxsiomin.common.presentation.UiText
import dev.maxsiomin.common.presentation.components.PermissionTextProvider
import dev.maxsiomin.prodhse.core.R

object LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): UiText {
        return if (isPermanentlyDeclined) {
            UiText.StringResource(R.string.location_permission_declined)
        } else {
            UiText.StringResource(R.string.location_permission_rationale)
        }
    }
}

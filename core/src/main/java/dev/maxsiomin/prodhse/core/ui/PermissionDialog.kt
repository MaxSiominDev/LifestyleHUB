package dev.maxsiomin.prodhse.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.maxsiomin.prodhse.core.R
import dev.maxsiomin.common.presentation.UiText

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.permission_required))
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                ).asString()
            )
        },
        confirmButton = {
            if (isPermanentlyDeclined) {
                Button(onClick = {
                    onGoToAppSettingsClick()
                }) {
                    Text(text = stringResource(R.string.grant_permission))
                }
            } else {
                Button(onClick = {
                    onOkClick()
                }) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            }
        }
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): UiText
}

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): UiText {
        return if (isPermanentlyDeclined) {
            UiText.StringResource(R.string.location_permission_declined)
        } else {
            UiText.StringResource(R.string.location_permission_rationale)
        }
    }
}

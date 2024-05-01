package dev.maxsiomin.prodhse.feature.auth.presentation.login

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.maxsiomin.prodhse.feature.auth.R

@Composable
internal fun ForgotPasswordDialog(
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.forgot_password))
        },
        text = {
            Text(text = stringResource(R.string.forgot_password_dialog_text))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
    )
}

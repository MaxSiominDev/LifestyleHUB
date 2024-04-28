package dev.maxsiomin.prodhse.feature.auth.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomActivity

@Composable
fun RandomActivityDialog(activity: RandomActivity, onDismissRequest: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.random_activity))
        },
        text = {
            val text = stringResource(
                id = R.string.random_activity_text,
                activity.name,
                activity.participants.toString()
            )
            Text(text = text)
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
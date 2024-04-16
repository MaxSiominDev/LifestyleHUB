package dev.maxsiomin.prodhse.feature.auth.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.maxsiomin.prodhse.feature.auth.R
import dev.maxsiomin.prodhse.feature.auth.domain.Holiday

@Composable
fun NearestHolidayDialog(holiday: Holiday, onDismissRequest: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.nearest_holiday))
        },
        text = {
            val text = stringResource(
                id = R.string.nearest_holiday_text,
                holiday.name,
                holiday.formattedDate,
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
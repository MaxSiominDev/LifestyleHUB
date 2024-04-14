package dev.maxsiomin.common.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.maxsiomin.common.R
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

/** Open source dialog */
@Composable
fun DatePickerDialog(
    date: LocalDate,
    onDateChange: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    var showDialog by rememberSaveable { mutableStateOf(false) }

    // When showDialog value changes to true, show the DatePickerDialog
    LaunchedEffect(showDialog) {
        if (showDialog) {
            val calendar = Calendar.getInstance().apply {
                time = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
            }
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            android.app.DatePickerDialog(context, { _, year, month, dayOfMonth ->
                // Update the date when a new date is selected
                val newDate = LocalDate.of(year, month + 1, dayOfMonth)
                onDateChange(newDate)
                showDialog = false
            }, currentYear, currentMonth, currentDay).show()
        }
    }

    // Optional: Use a Button or any Composable to trigger the dialog
    Button(onClick = { showDialog = true }) {
        Text(text = stringResource(id = R.string.select_date))
    }
}
package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.maxsiomin.prodhse.core.ui.DatePickerDialog
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import java.time.LocalDate

@Composable
internal fun AddPlanScreen(fsqId: String) {

    LaunchedEffect(fsqId) {

    }

    Column {

    }

    var place by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current

    Column {
        TextField(
            value = place,
            onValueChange = { place = it },
            label = { Text("Place") },
            singleLine = true
        )

        DatePickerDialog(
            date = selectedDate,
            onDateChange = { selectedDate = it }
        )

        TextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note") },
            modifier = Modifier.weight(1f),
            keyboardActions = KeyboardActions.Default
        )

        Button(onClick = {
            // Here you can handle the save action
            // For example, save the data to a database or call an API
        }) {
            Text(text = "Save")
        }
    }

}

@Preview
@Composable
private fun AddPlanScreenPreview() {
    ProdhseTheme {
        AddPlanScreen(
            fsqId = "",
        )
    }
}
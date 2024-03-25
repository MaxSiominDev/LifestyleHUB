package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.maxsiomin.prodhse.core.ui.DatePickerDialog
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.presentation.home.DetailsViewModel
import java.time.LocalDate

/** This screen is in planner package instead of home, since its logic is more about plans than about venues.
 * In spite of it, screen is located at home TLD in navigation graph (it makes sense because user can get to this screen
 * only from destinations of home TLD.
 */
@Composable
internal fun AddPlanScreen(fsqId: String, state: AddPlanViewModel.State, onEvent: (AddPlanViewModel.Event) -> Unit) {

    LaunchedEffect(fsqId) {
        onEvent(AddPlanViewModel.Event.PassPlaceId(fsqId))
    }

    var place by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val placeDetails = state.placeDetails ?: return

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = placeDetails.name,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = state.dateString)

            Spacer(Modifier.width(12.dp))

            DatePickerDialog(
                date = state.dateLocalDate,
                onDateChange = { onEvent(AddPlanViewModel.Event.NewDateSelected(it)) }
            )
        }

        TextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note") },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        )

        Button(onClick = {

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
            state = AddPlanViewModel.State(
                placeDetails = PlaceDetailsModel(
                    name = "Кафе Studio 89.5",
                    address = "Маросейка, д. 13, 101000, Москва",
                    photos = listOf(
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                        PhotoModel(id = "", url = ""),
                    ),
                    workingHours = listOf(
                        "Mon-Thu 11:00-23:00",
                        "Fri 11:00-24:00",
                        "Sat-Sun 0:00-2:00",
                    ),
                    isVerified = true,
                    rating = 9.3,
                    website = "https://megapolism.ru",
                    isOpenNow = true,
                    fsqId = "",
                    categories = "Cafe",
                    timeUpdated = System.currentTimeMillis(),
                ),
                dateString = "March 26, 2024",
                dateMillis = System.currentTimeMillis(),
                dateLocalDate = LocalDate.now(),
            ),
            onEvent = {}
        )
    }
}
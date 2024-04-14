package dev.maxsiomin.prodhse.feature.home.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.common.presentation.SnackbarInfo
import dev.maxsiomin.common.presentation.components.DatePickerDialog
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.home.domain.PlaceDetailsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

@Composable
internal fun AddPlanScreen(
    fsqId: String,
    navController: NavController,
    snackbarCallback: SnackbarCallback,
    state: AddPlanViewModel.State,
    eventFlow: Flow<AddPlanViewModel.UiEvent>,
    onEvent: (AddPlanViewModel.Event) -> Unit
) {

    CollectFlow(eventFlow) { event ->
        when (event) {
            is AddPlanViewModel.UiEvent.NavigateBack -> navController.popBackStack()
            is AddPlanViewModel.UiEvent.ShowSnackbar -> snackbarCallback(
                SnackbarInfo(message = event.message)
            )
        }
    }

    LaunchedEffect(fsqId) {
        onEvent(AddPlanViewModel.Event.PassPlaceId(fsqId))
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
    ) {
        val placeDetails = state.placeDetails ?: return@Column
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

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.title)) },
            value = state.noteTitle,
            onValueChange = {
                onEvent(AddPlanViewModel.Event.NoteTitleChanged(it))
            },
        )

        TextField(
            value = state.noteText,
            onValueChange = {
                onEvent(AddPlanViewModel.Event.NoteTextChanged(it))
            },
            label = { Text(text = stringResource(R.string.description)) },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        )

        Button(onClick = {
            onEvent(AddPlanViewModel.Event.SaveClicked)
        }) {
            Text(text = stringResource(id = R.string.save))
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.isError) {
            Button(
                onClick = {
                    // Actually refreshes the data
                    onEvent(AddPlanViewModel.Event.PassPlaceId(fsqId))
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.refresh))
            }
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
                    phone = "+79151236547",
                    email = "rokymiel@gmail.com"
                ),
                dateString = "March 26, 2024",
                dateLocalDate = LocalDate.now(),
            ),
            onEvent = {},
            navController = rememberNavController(),
            eventFlow = flow { },
            snackbarCallback = {}
        )
    }
}
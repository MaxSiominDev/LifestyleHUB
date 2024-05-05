package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.add_plan

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.common.presentation.SnackbarInfo
import dev.maxsiomin.common.presentation.components.DatePickerDialog
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.core.presentation.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import java.time.LocalDate

@Composable
internal fun AddPlanScreenRoot(
    navController: NavController,
    snackbarCallback: SnackbarCallback,
    viewModel: AddPlanViewModel = hiltViewModel(),
) {

    CollectFlow(viewModel.effectFlow) { effect ->
        when (effect) {
            is AddPlanViewModel.Effect.NavigateBack -> navController.popBackStack()
            is AddPlanViewModel.Effect.ShowMessage -> snackbarCallback(
                SnackbarInfo(message = effect.message)
            )
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    AddPlanScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun AddPlanScreen(
    state: AddPlanViewModel.State,
    onEvent: (AddPlanViewModel.Event) -> Unit,
) {
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
                    onEvent(AddPlanViewModel.Event.Refresh)
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
            state = AddPlanViewModel.State(
                placeDetails = PlaceDetails(
                    name = "Кафе Studio 89.5",
                    address = "Маросейка, д. 13, 101000, Москва",
                    photos = listOf(
                        Photo(id = "", url = ""),
                        Photo(id = "", url = ""),
                        Photo(id = "", url = ""),
                        Photo(id = "", url = ""),
                        Photo(id = "", url = ""),
                        Photo(id = "", url = ""),
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
            onEvent = {}
        )
    }
}
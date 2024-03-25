package dev.maxsiomin.prodhse.feature.venues.presentation.planner

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.maxsiomin.prodhse.core.ui.DatePickerDialog
import dev.maxsiomin.prodhse.core.util.CollectFlow
import dev.maxsiomin.prodhse.core.util.SnackbarCallback
import dev.maxsiomin.prodhse.core.util.SnackbarInfo
import dev.maxsiomin.prodhse.feature.venues.R
import dev.maxsiomin.prodhse.feature.venues.presentation.home.AddPlanViewModel
import kotlinx.coroutines.flow.Flow

@Composable
internal fun EditPlanScreen(
    state: EditPlanViewModel.State,
    eventsFlow: Flow<EditPlanViewModel.UiEvent>,
    onEvent: (EditPlanViewModel.Event) -> Unit,
    showSnackbar: SnackbarCallback,
    navController: NavController,
    planId: Long,
) {

    CollectFlow(eventsFlow) { event ->
        when (event) {
            is EditPlanViewModel.UiEvent.NavigateBack -> {
                navController.popBackStack()
            }

            is EditPlanViewModel.UiEvent.ShowSnackbar -> {
                showSnackbar(SnackbarInfo(event.message))
            }
        }
    }

    LaunchedEffect(planId) {
        onEvent(EditPlanViewModel.Event.PassPlanId(planId))
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
                onDateChange = { onEvent(EditPlanViewModel.Event.NewDateSelected(it)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.title)) },
            value = state.noteTitle,
            onValueChange = {
                onEvent(EditPlanViewModel.Event.NoteTitleChanged(it))
            },
        )

        TextField(
            value = state.noteText,
            onValueChange = {
                onEvent(EditPlanViewModel.Event.NoteTextChanged(it))
            },
            label = { Text(text = stringResource(R.string.description)) },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        )

        Button(
            enabled = state.isNotSaved,
            onClick = {
                onEvent(EditPlanViewModel.Event.SaveClicked)
            },
        ) {
            Text(text = stringResource(R.string.save))
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.isError) {
            Button(
                onClick = {
                    // Actually refreshes the data
                    onEvent(EditPlanViewModel.Event.PassPlanId(planId))
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
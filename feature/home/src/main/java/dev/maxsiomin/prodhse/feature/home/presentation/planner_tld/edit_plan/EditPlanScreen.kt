package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.edit_plan

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.maxsiomin.common.presentation.SnackbarCallback
import dev.maxsiomin.common.presentation.SnackbarInfo
import dev.maxsiomin.common.presentation.components.DatePickerDialog
import dev.maxsiomin.common.util.CollectFlow
import dev.maxsiomin.prodhse.feature.home.R

@Composable
internal fun EditPlanScreenRoot(
    showSnackbar: SnackbarCallback,
    navController: NavController,
    viewModel: EditPlanViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectFlow(viewModel.effectFlow) { effect ->
        when (effect) {
            is EditPlanViewModel.Effect.NavigateBack -> {
                navController.popBackStack()
            }

            is EditPlanViewModel.Effect.ShowMessage -> {
                showSnackbar(SnackbarInfo(effect.message))
            }
        }
    }

    EditPlanScreen(state = state, onEvent = viewModel::onEvent)

}

@Composable
private fun EditPlanScreen(
    state: EditPlanViewModel.State,
    onEvent: (EditPlanViewModel.Event) -> Unit
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
                    onEvent(EditPlanViewModel.Event.Refresh)
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
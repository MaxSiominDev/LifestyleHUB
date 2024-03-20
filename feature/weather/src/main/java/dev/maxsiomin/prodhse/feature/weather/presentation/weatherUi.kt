package dev.maxsiomin.prodhse.feature.weather.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxsiomin.prodhse.core.CollectFlow
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.core.SnackbarInfo
import dev.maxsiomin.prodhse.core.UiText
import dev.maxsiomin.prodhse.feature.weather.R

@Composable
fun weatherUi(showSnackbar: SnackbarCallback, endRefresh: () -> Unit): UpdateCallback {

    val viewModel: WeatherViewModel = hiltViewModel()

    CollectFlow(viewModel.eventFlow) { event ->
        when (event) {
            is WeatherViewModel.UiEvent.FetchingError -> {
                showSnackbar(SnackbarInfo(UiText.DynamicString(event.message)))
            }
        }
    }

    viewModel.endRefreshCallback = endRefresh

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { viewModel.onEvent(WeatherViewModel.Event.ExpandStateChanged) },
        ) {
            Icon(
                imageVector = if (viewModel.state.isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (viewModel.state.isExpanded) "Collapse" else "Expand"
            )
        }
        if (viewModel.state.isExpanded.not()) {
            Text(text = stringResource(id = R.string.weather))
        }
    }

    Box(
        Modifier
            .animateContentSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
    {
        if (viewModel.state.isExpanded) {
            val weather = viewModel.state.weather

            when {
                weather != null && viewModel.state.weatherStatus is WeatherViewModel.WeatherStatus.Success -> {
                    WeatherCard(
                        weather = weather,
                        WeatherViewModel.WeatherStatus.Success
                    )
                }

                viewModel.state.weatherStatus is WeatherViewModel.WeatherStatus.Loading -> {
                    WeatherCard(
                        weather = viewModel.emptyCardContent,
                        weatherStatus = WeatherViewModel.WeatherStatus.Loading
                    )
                }

                else -> {
                    WeatherCard(
                        weather = viewModel.emptyCardContent,
                        weatherStatus = WeatherViewModel.WeatherStatus.Error
                    )
                }
            }
        }
    }

    return {
        viewModel.onEvent(WeatherViewModel.Event.RefreshWeather)
    }
}

typealias UpdateCallback = () -> Unit
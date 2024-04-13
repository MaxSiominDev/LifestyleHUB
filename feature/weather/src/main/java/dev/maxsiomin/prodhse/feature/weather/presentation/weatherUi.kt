package dev.maxsiomin.prodhse.feature.weather.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxsiomin.prodhse.core.util.CollectFlow
import dev.maxsiomin.prodhse.core.util.SnackbarCallback
import dev.maxsiomin.prodhse.core.util.SnackbarInfo
import dev.maxsiomin.prodhse.core.util.UiText
import dev.maxsiomin.prodhse.feature.weather.R

@Composable
fun weatherUi(
    showSnackbar: SnackbarCallback,
    viewModel: WeatherViewModel = hiltViewModel()
): UpdateCallback {

    CollectFlow(viewModel.eventsFlow) { event ->
        when (event) {
            is WeatherViewModel.UiEvent.FetchingError -> {
                showSnackbar(SnackbarInfo(UiText.DynamicString(event.message)))
            }
        }
    }

    //viewModel.endRefreshCallback = endRefresh

    val isExpanded = viewModel.state.isExpanded
    val bottomPadding = if (isExpanded) 16.dp else 0.dp
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = bottomPadding)
            .clickable {
                viewModel.onEvent(WeatherViewModel.Event.ExpandStateChanged)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = if (isExpanded) "Collapse" else "Expand"
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = stringResource(id = R.string.weather))
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
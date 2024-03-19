package dev.maxsiomin.prodhse.feature.weather.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxsiomin.prodhse.core.SnackbarCallback

@Composable
fun weatherUi(showSnackbar: SnackbarCallback): UpdateCallback {

    val viewModel: WeatherViewModel = hiltViewModel()
    WeatherWidget(viewModel.state, viewModel.eventFlow, viewModel::onEvent, showSnackbar)

    return {
        viewModel.onEvent(WeatherViewModel.Event.RefreshWeather)
    }
}

typealias UpdateCallback = () -> Unit
package dev.maxsiomin.prodhse.feature.weather.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.maxsiomin.prodhse.core.CollectFlow
import dev.maxsiomin.prodhse.core.SnackbarInfo
import dev.maxsiomin.prodhse.core.UiText
import kotlinx.coroutines.flow.Flow

@Composable
internal fun WeatherWidget(
    state: WeatherViewModel.State,
    eventsFlow: Flow<WeatherViewModel.UiEvent>,
    onEvent: (WeatherViewModel.Event) -> Unit,
    showSnackbar: (SnackbarInfo) -> Unit,
) {

    CollectFlow(eventsFlow) { event ->
        when (event) {
            is WeatherViewModel.UiEvent.FetchingError -> {
                showSnackbar(SnackbarInfo(UiText.DynamicString(event.message)))
            }
        }
    }

    when {
        state.weather != null && state.weatherStatus is WeatherViewModel.WeatherStatus.Success -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(state.weather.backgroundColor),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.weather.city, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = state.weather.weatherCondition.iconUrl,
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = state.weather.temperatureInfo.range)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Now: ${state.weather.temperatureInfo.current}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Feels like: ${state.weather.temperatureInfo.feelsLike}")
                }
            }


        }

        else -> {
            println()
        }

    }

}

package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

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
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home.HomeViewModel
import dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home.WeatherCard

@Composable
internal fun WeatherItem(
    state: HomeViewModel.State,
    onEvent: (HomeViewModel.Event) -> Unit,
) {

    val isExpanded = state.weatherIsExpanded
    val bottomPadding = if (isExpanded) 16.dp else 0.dp
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = bottomPadding)
            .clickable {
                onEvent(HomeViewModel.Event.ExpandStateChanged)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(
                id = if (isExpanded) R.string.collapse else R.string.expand
            )
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = stringResource(id = R.string.weather))
    }

    Box(
        Modifier
            .animateContentSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        if (state.weatherIsExpanded) {
            WeatherCard(
                weather = state.weather,
                weatherStatus = state.weatherStatus,
            )
        }
    }
}

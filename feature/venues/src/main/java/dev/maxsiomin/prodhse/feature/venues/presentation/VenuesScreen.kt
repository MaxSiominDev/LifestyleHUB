package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.maxsiomin.prodhse.core.SnackbarCallback
import dev.maxsiomin.prodhse.feature.weather.presentation.weatherUi

@Composable
internal fun VenuesScreen(showSnackbar: SnackbarCallback) {

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val updateCallback = weatherUi(
            showSnackbar = showSnackbar
        )
    }

}
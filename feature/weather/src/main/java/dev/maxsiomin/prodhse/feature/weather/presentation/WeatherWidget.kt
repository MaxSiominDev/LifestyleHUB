package dev.maxsiomin.prodhse.feature.weather.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WeatherWidget(modifier: Modifier = Modifier) {

    val viewModel: WeatherViewModel = hiltViewModel()
    Row(modifier) {

    }

}
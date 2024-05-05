package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.maxsiomin.common.presentation.components.blueShimmerColors
import dev.maxsiomin.prodhse.core.presentation.theme.ProdhseTheme
import dev.maxsiomin.common.presentation.components.shimmerEffect
import dev.maxsiomin.prodhse.feature.home.R
import dev.maxsiomin.prodhse.feature.home.domain.model.TemperatureInfo
import dev.maxsiomin.prodhse.feature.home.domain.model.WeatherCondition
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.presentation.theme.DayBackground
import dev.maxsiomin.prodhse.feature.home.presentation.theme.DayTextColor
import dev.maxsiomin.prodhse.feature.home.presentation.theme.NightBackground
import dev.maxsiomin.prodhse.feature.home.presentation.theme.NightTextColor

@Composable
internal fun WeatherCard(weather: Weather, weatherStatus: HomeViewModel.WeatherStatus) {

    val isPreview = LocalInspectionMode.current

    val isNight = weather.weatherCondition.isNight
    val background = remember(isNight) {
        if (isNight) NightBackground else DayBackground
    }
    val textColor = remember(isNight) {
        if (isNight) NightTextColor else DayTextColor
    }

    val isLoading = weatherStatus == HomeViewModel.WeatherStatus.Loading
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(20.dp))
                .shimmerEffect(blueShimmerColors),
        )
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = background,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Left-aligned content
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(
                    text = weather.date,
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = weather.city,
                    color = textColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                )

                Spacer(modifier = Modifier.height(16.dp))
                if (weatherStatus == HomeViewModel.WeatherStatus.Success) {
                    Text(text = weather.temperatureInfo.range, color = textColor)
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Now: ${weather.temperatureInfo.current}", color = textColor)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Feels like: ${weather.temperatureInfo.feelsLike}",
                    color = textColor,
                )
            }

            // Right-aligned content
            if (weatherStatus == HomeViewModel.WeatherStatus.Success) {
                Column(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    horizontalAlignment = Alignment.End
                ) {
                    val imageModifier = Modifier.size(48.dp)
                    if (LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(id = R.drawable.d03),
                            contentDescription = null,
                            modifier = imageModifier,
                        )
                    } else {
                        AsyncImage(
                            model = weather.weatherCondition.iconUrl,
                            contentDescription = stringResource(R.string.weather_icon),
                            modifier = imageModifier,
                        )
                    }

                    Text(
                        text = weather.weatherCondition.name,
                        color = textColor,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else if (weatherStatus == HomeViewModel.WeatherStatus.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@Preview
@Composable
private fun WeatherSuccessCardPreview() {
    ProdhseTheme {
        WeatherCard(
            Weather(
                city = "Chertanovo",
                temperatureInfo = TemperatureInfo(
                    current = "+5°",
                    feelsLike = "+2°",
                    range = "-4°...+5°",
                ),
                weatherCondition = WeatherCondition(
                    name = "scattered clouds",
                    iconUrl = null,
                    isNight = true
                ),
                date = "Wednesday, 20 March"
            ),
            weatherStatus = HomeViewModel.WeatherStatus.Success,
        )
    }
}
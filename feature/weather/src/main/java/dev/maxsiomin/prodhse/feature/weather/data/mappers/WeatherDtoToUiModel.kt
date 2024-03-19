package dev.maxsiomin.prodhse.feature.weather.data.mappers

import androidx.compose.ui.graphics.Color
import dev.maxsiomin.prodhse.feature.weather.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.weather.domain.TemperatureInfo
import dev.maxsiomin.prodhse.feature.weather.domain.WeatherCondition
import dev.maxsiomin.prodhse.feature.weather.domain.WeatherModel
import kotlin.math.roundToInt

internal class WeatherDtoToUiModel : (CurrentWeatherResponse) -> WeatherModel {

    override fun invoke(weatherDto: CurrentWeatherResponse): WeatherModel {

        val city = weatherDto.name ?: UNKNOWN

        val currentTemperature = weatherDto.main?.temp?.formatTemperature() ?: UNKNOWN

        val minTemp = weatherDto.main?.tempMin?.formatTemperature()
        val maxTemp = weatherDto.main?.tempMax?.formatTemperature()
        val range = if (minTemp != null && maxTemp != null) "$minTemp...$maxTemp" else ""

        val feelsLike = weatherDto.main?.feelsLike?.formatTemperature() ?: UNKNOWN

        val temperatureInfo = TemperatureInfo(
            range = range,
            current = currentTemperature,
            feelsLike = feelsLike,
        )


        val weatherFromDto = weatherDto.weather?.firstOrNull()
        val weatherType = weatherFromDto?.description ?: UNKNOWN

        val weatherIcon = weatherDto.weather?.firstOrNull()?.icon

        val weatherCondition = WeatherCondition(
            name = weatherType,
            iconUrl = "https://openweathermap.org/img/wn/$weatherIcon.png",
        )

        val isNight = weatherIcon?.lastOrNull()?.toString() == "n"

        return WeatherModel(
            city = city,
            weatherCondition = weatherCondition,
            temperatureInfo = temperatureInfo,
            backgroundColor = if (isNight) nightBg else dayBg
        )
    }

    private fun Double.formatTemperature(): String {
        val int = this.roundToInt()
        val str = when {
            int > 0 -> "+$int"
            else -> "$int"
        }
        return "$str°"
    }

    companion object {
        private const val UNKNOWN = "–"

        private val dayBg = Color.Cyan
        private val nightBg = Color.Blue
    }
}

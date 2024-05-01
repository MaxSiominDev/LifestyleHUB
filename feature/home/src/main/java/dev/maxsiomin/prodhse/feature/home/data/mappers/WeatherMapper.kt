package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.home.domain.model.TemperatureInfo
import dev.maxsiomin.prodhse.feature.home.domain.model.WeatherCondition
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

internal class WeatherMapper @Inject constructor() : ToDomainMapper<CurrentWeatherResponse, Weather> {

    override fun toDomain(data: CurrentWeatherResponse): Weather {
        return Weather(
            city = getCity(data),
            weatherCondition = getWeatherCondition(data),
            temperatureInfo = getTemperatureInfo(data),
            date = getDate(),
        )
    }

    private fun getCity(weatherDto: CurrentWeatherResponse): String {
        return weatherDto.name ?: UNKNOWN
    }

    private fun getWeatherCondition(weatherDto: CurrentWeatherResponse): WeatherCondition {
        val weatherFromDto = weatherDto.weather?.firstOrNull()
        var weatherType = weatherFromDto?.description
        if (weatherType.isNullOrBlank()) {
            weatherType = UNKNOWN
        } else {
            val firstLetter = weatherType.first()
            weatherType = weatherType.replaceFirst(firstLetter, firstLetter.uppercaseChar())
        }

        val weatherIcon = weatherDto.weather?.firstOrNull()?.icon

        val lastLetterInIconUrl = weatherIcon?.lastOrNull()?.toString()
        val lastLetterIsNight = lastLetterInIconUrl?.equals("n")

        return WeatherCondition(
            name = weatherType,
            iconUrl = "https://openweathermap.org/img/wn/$weatherIcon.png",
            isNight = lastLetterIsNight ?: false
        )
    }

    private fun getTemperatureInfo(weatherDto: CurrentWeatherResponse): TemperatureInfo {
        val currentTemperature = weatherDto.main?.temp?.formatTemperature() ?: UNKNOWN

        val minTemp = weatherDto.main?.tempMin?.formatTemperature()
        val maxTemp = weatherDto.main?.tempMax?.formatTemperature()
        val range = if (minTemp != null && maxTemp != null) "$minTemp...$maxTemp" else ""

        val feelsLike = weatherDto.main?.feelsLike?.formatTemperature() ?: UNKNOWN

        return TemperatureInfo(
            range = range,
            current = currentTemperature,
            feelsLike = feelsLike,
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

    private fun getDate(): String {
        val currentDate = Date()
        val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val dayOfWeek = dayOfWeekFormat.format(currentDate)

        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val monthName = monthFormat.format(calendar.time)

        // Example: Wednesday, 20 March
        return "$dayOfWeek, $dayOfMonth $monthName"
    }

    companion object {
        private const val UNKNOWN = "–"
    }
}

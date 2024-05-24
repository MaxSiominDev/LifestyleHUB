package dev.maxsiomin.prodhse.feature.home.data.mappers

import com.google.common.truth.Truth.assertThat
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.Main
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.Weather
import org.junit.Test

class WeatherMapperTest {

    @Test
    fun `toDomain returns Weather with valid data`() {
        // Given
        val weatherDto = createWeatherDto()
        val mapper = WeatherMapper()

        // When
        val result = mapper.toDomain(weatherDto)

        // Then
        assertThat(result.city).isEqualTo("Mountain View")
        assertThat(result.weatherCondition.name).isEqualTo("Haze")
        assertThat(result.weatherCondition.iconUrl).isEqualTo("https://openweathermap.org/img/wn/50d.png")
        assertThat(result.weatherCondition.isNight).isFalse()
        assertThat(result.temperatureInfo.current).isEqualTo("+10°")
        assertThat(result.temperatureInfo.range).isEqualTo("+8°...+12°")
        assertThat(result.temperatureInfo.feelsLike).isEqualTo("+9°")
        assertThat(result.date).isEqualTo("Friday, 24 May")
    }

    // Helper method to create a sample CurrentWeatherResponse object
    private fun createWeatherDto(): CurrentWeatherResponse {
        return CurrentWeatherResponse(
            name = "Mountain View",
            weather = listOf(Weather(id = 721, main = "Haze", description = "haze", icon = "50d")),
            main = Main(temp = 10.07, feelsLike = 9.33, tempMin = 8.13, tempMax = 11.85, pressure = 1012, humidity = 84, seaLevel = null, grndLevel = null),
            dt = 1621875123
        )
    }

    @Test
    fun `toDomain sets isNight correctly`() {
        // Given
        val mapper = WeatherMapper()

        val dayWeatherDto = CurrentWeatherResponse(weather = listOf(Weather(icon = "01d"))) // Weather icon indicating daytime
        val nightWeatherDto = CurrentWeatherResponse(weather = listOf(Weather(icon = "01n"))) // Weather icon indicating nighttime

        // When
        val dayResult = mapper.toDomain(dayWeatherDto)
        val nightResult = mapper.toDomain(nightWeatherDto)

        // Then
        assertThat(dayResult.weatherCondition.isNight).isFalse()
        assertThat(nightResult.weatherCondition.isNight).isTrue()
    }

}

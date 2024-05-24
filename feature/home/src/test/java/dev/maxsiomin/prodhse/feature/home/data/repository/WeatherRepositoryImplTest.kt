package dev.maxsiomin.prodhse.feature.home.data.repository

import com.google.common.truth.Truth.assertThat
import dev.maxsiomin.common.domain.resource.DataError
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.StandardDispatchers
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.Clouds
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.Coord
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.CurrentWeatherResponse
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.Main
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.Sys
import dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.Wind
import dev.maxsiomin.prodhse.feature.home.data.mappers.WeatherMapper
import dev.maxsiomin.prodhse.feature.home.data.remote.weather_api.WeatherApi
import dev.maxsiomin.prodhse.feature.home.domain.model.TemperatureInfo
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.domain.model.WeatherCondition
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var repo: WeatherRepositoryImpl
    private lateinit var mockApi: WeatherApi
    private lateinit var mockWeatherMapper: WeatherMapper

    @Before
    fun setup() {
        mockApi = mockk()
        mockWeatherMapper = mockk()
        repo = WeatherRepositoryImpl(mockApi, mockWeatherMapper, StandardDispatchers)
    }

    private fun getDomainWeather(): Weather {
        val city = "Mountain View"
        val current = "+10"
        val range = "+8..+11"
        val feelsLike = "+10"
        val temperatureInfo = TemperatureInfo(
            current = current,
            range = range,
            feelsLike = feelsLike,
        )
        val name = "Haze"
        val iconUrl = null
        val isNight = true
        val weatherCondition = WeatherCondition(
            name = name,
            iconUrl = iconUrl,
            isNight = isNight,
        )
        val expectedWeather = Weather(
            city = city,
            temperatureInfo = temperatureInfo,
            weatherCondition = weatherCondition,
            date = "May 24, 2024"
        )
        return expectedWeather
    }

    private fun getDataWeather(): CurrentWeatherResponse {
        val currentWeatherResponse = CurrentWeatherResponse(
            coord = Coord(lon = 1.0, lat = 1.0),
            weather = listOf(
                dev.maxsiomin.prodhse.feature.home.data.dto.current_weather_response.Weather(
                    id = 721, main = "Haze", description = "haze", icon = null
                )
            ),
            base = "stations",
            main = Main(temp = 10.07, feelsLike = 9.33, tempMin = 8.13, tempMax = 11.85, pressure = 1012, humidity = 84, seaLevel = null, grndLevel = null),
            visibility = 8047,
            wind = Wind(speed = 3.6, deg = 260, gust = null),
            rain = null,
            clouds = Clouds(all = 0),
            dt = 1716555949,
            sys = Sys(type = 2, id = 2010364, country = "US", sunrise = 1716555163, sunset = 1716607085),
            timezone = -25200,
            id = 5375480,
            name = "Mountain View",
            cod = 200
        )
        return currentWeatherResponse
    }

    @Test
    fun `should return success`() = runTest {

        val lat = "1.0"
        val lon = "1.0"
        val lang = "en"

        val expectedResult = getDomainWeather()
        val inputFromApi = getDataWeather()

        coEvery {
            mockApi.getCurrentWeather(lat = lat, lon = lon, lang = lang)
        } returns Resource.Success(inputFromApi)

        every { mockWeatherMapper.toDomain(inputFromApi) } returns getDomainWeather()

        val result = repo.getCurrentWeather(lat = lat, lon = lon, lang = lang)
        assertThat(result).isEqualTo(Resource.Success<Weather, DataError>(expectedResult))

        coVerify { mockApi.getCurrentWeather(lat = lat, lon = lon, lang = lang) }
        verify { mockWeatherMapper.toDomain(inputFromApi) }

    }

    @Test
    fun `should return network error`() = runTest {
        val lat = "1.0"
        val lon = "1.0"
        val lang = "en"

        coEvery {
            mockApi.getCurrentWeather(lat = lat, lon = lon, lang = lang)
        } returns Resource.Error(NetworkError.Server)

        val result = repo.getCurrentWeather(lat = lat, lon = lon, lang = lang)
        assertThat(result).isEqualTo(Resource.Error<Weather, DataError>(NetworkError.Server))

        coVerify { mockApi.getCurrentWeather(lat = lat, lon = lon, lang = lang) }

    }

}
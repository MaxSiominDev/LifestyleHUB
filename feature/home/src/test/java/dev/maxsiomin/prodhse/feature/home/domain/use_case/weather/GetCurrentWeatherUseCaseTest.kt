package dev.maxsiomin.prodhse.feature.home.domain.use_case.weather

import com.google.common.truth.Truth.assertThat
import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.core.util.StandardDispatchers
import dev.maxsiomin.prodhse.feature.home.domain.model.TemperatureInfo
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.domain.model.WeatherCondition
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCurrentWeatherUseCaseTest {

    private val lang = "en"

    private lateinit var localeManager: LocaleManager
    private lateinit var dummyRepo: WeatherRepository
    private lateinit var useCase: GetCurrentWeatherUseCase

    @Before
    fun setup() {
        dummyRepo = mockk()

        localeManager = mockk()
        every { localeManager.getLocaleLanguage() } returns lang

        useCase = GetCurrentWeatherUseCase(
            dummyRepo,
            localeManager,
            StandardDispatchers,
        )
    }

    @Test
    fun `should return success result`() = runTest {
        val lat = "1.0"
        val lon = "2.0"

        val city = "Moscow"
        val current = "+15"
        val range = "12..13"
        val feelsLike = "+10"
        val temperatureInfo = TemperatureInfo(
            current = current,
            range = range,
            feelsLike = feelsLike,
        )
        val name = "Cloudy"
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

        coEvery {
            dummyRepo.getCurrentWeather(lat = lat, lon = lon, lang = lang)
        } returns Resource.Success(expectedWeather)

        assertThat(useCase(lat = lat.toDouble(), lon = lon.toDouble()))
            .isEqualTo(Resource.Success<Weather, NetworkError>(expectedWeather))

        coVerify { dummyRepo.getCurrentWeather(lat = lat, lon = lon, lang = lang) }
        coVerify { localeManager.getLocaleLanguage() }
    }

    @Test
    fun `should return network error result`() = runTest {
        val lat = "1.0"
        val lon = "2.0"

        coEvery {
            dummyRepo.getCurrentWeather(lat = lat, lon = lon, lang = lang)
        } returns Resource.Error(NetworkError.Server)

        assertThat(useCase(lat = lat.toDouble(), lon = lon.toDouble()))
            .isEqualTo(Resource.Error<Weather, NetworkError>(NetworkError.Server))

        coVerify { dummyRepo.getCurrentWeather(lat = lat, lon = lon, lang = lang) }
        coVerify { localeManager.getLocaleLanguage() }
    }

}
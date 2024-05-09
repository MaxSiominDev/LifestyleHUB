package dev.maxsiomin.prodhse.feature.home.domain.use_case.weather

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepo: WeatherRepository,
    private val localeManager: LocaleManager,
) {

    suspend operator fun invoke(
        lat: Double,
        lon: Double
    ): Resource<Weather, NetworkError> = withContext(Dispatchers.IO) {
        val lang = localeManager.getLocaleLanguage()
        return@withContext weatherRepo.getCurrentWeather(lat = lat.toString(), lon = lon.toString(), lang = lang)
    }

}
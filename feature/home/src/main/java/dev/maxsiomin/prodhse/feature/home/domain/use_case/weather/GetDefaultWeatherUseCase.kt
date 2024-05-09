package dev.maxsiomin.prodhse.feature.home.domain.use_case.weather

import dev.maxsiomin.prodhse.feature.home.domain.model.Weather
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository
import javax.inject.Inject

internal class GetDefaultWeatherUseCase @Inject constructor(
    private val weatherRepo: WeatherRepository
) {

    operator fun invoke(): Weather {
        return weatherRepo.getDefaultWeather()
    }

}
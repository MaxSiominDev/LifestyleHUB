package dev.maxsiomin.prodhse.feature.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.prodhse.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WeatherViewModel @Inject constructor(private val repo: WeatherRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            repo.getCurrentWeather("45", "50", "ru").collect {
                println(it)
            }
        }
    }

}
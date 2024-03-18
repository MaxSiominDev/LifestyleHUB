package dev.maxsiomin.prodhse.feature.weather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.weather.data.remote.WeatherApi
import dev.maxsiomin.prodhse.feature.weather.data.remote.WeatherApiImpl
import dev.maxsiomin.prodhse.feature.weather.data.repository.WeatherRepositoryImpl
import dev.maxsiomin.prodhse.feature.weather.domain.repository.WeatherRepository
import io.ktor.client.HttpClient

@InstallIn(ViewModelComponent::class)
@Module
internal object WeatherModule {

    @Provides
    @ViewModelScoped
    fun provideWeatherApi(impl: WeatherApiImpl): WeatherApi = impl

    @Provides
    @ViewModelScoped
    fun provideWeatherRepo(impl: WeatherRepositoryImpl): WeatherRepository = impl

}
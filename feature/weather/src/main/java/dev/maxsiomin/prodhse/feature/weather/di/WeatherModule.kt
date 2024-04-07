package dev.maxsiomin.prodhse.feature.weather.di

import dagger.Binds
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
internal abstract class WeatherModule {

    @Binds
    @ViewModelScoped
    abstract fun provideWeatherApi(impl: WeatherApiImpl): WeatherApi

    @Binds
    @ViewModelScoped
    abstract fun provideWeatherRepo(impl: WeatherRepositoryImpl): WeatherRepository

}
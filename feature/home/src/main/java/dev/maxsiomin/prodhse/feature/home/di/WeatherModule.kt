package dev.maxsiomin.prodhse.feature.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class WeatherModule {

    @Binds
    @ViewModelScoped
    abstract fun provideWeatherApi(impl: dev.maxsiomin.prodhse.feature.home.data.remote.weather_api.WeatherApiImpl): dev.maxsiomin.prodhse.feature.home.data.remote.weather_api.WeatherApi

    @Binds
    @ViewModelScoped
    abstract fun provideWeatherRepo(impl: dev.maxsiomin.prodhse.feature.home.data.repository.WeatherRepositoryImpl): WeatherRepository

}
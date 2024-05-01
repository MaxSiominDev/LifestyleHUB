package dev.maxsiomin.prodhse.feature.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.home.data.remote.places_api.PlacesApi
import dev.maxsiomin.prodhse.feature.home.data.remote.places_api.PlacesApiImpl
import dev.maxsiomin.prodhse.feature.home.data.remote.weather_api.WeatherApi
import dev.maxsiomin.prodhse.feature.home.data.remote.weather_api.WeatherApiImpl
import dev.maxsiomin.prodhse.feature.home.data.repository.LocationRepositoryImpl
import dev.maxsiomin.prodhse.feature.home.data.repository.PlacesRepositoryImpl
import dev.maxsiomin.prodhse.feature.home.data.repository.PlansRepositoryImpl
import dev.maxsiomin.prodhse.feature.home.data.repository.WeatherRepositoryImpl
import dev.maxsiomin.prodhse.feature.home.domain.repository.LocationRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlansRepository
import dev.maxsiomin.prodhse.feature.home.domain.repository.WeatherRepository

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class RepositoryAndApisModule {

    @Binds
    @ViewModelScoped
    abstract fun providePlacesApi(impl: PlacesApiImpl): PlacesApi

    @Binds
    @ViewModelScoped
    abstract fun providePlacesRepository(impl: PlacesRepositoryImpl): PlacesRepository

    @Binds
    @ViewModelScoped
    abstract fun providePlansRepository(impl: PlansRepositoryImpl): PlansRepository

    @Binds
    @ViewModelScoped
    abstract fun provideWeatherApi(impl: WeatherApiImpl): WeatherApi

    @Binds
    @ViewModelScoped
    abstract fun provideWeatherRepo(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @ViewModelScoped
    abstract fun provideLocationRepo(impl: LocationRepositoryImpl): LocationRepository

}
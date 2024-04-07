package dev.maxsiomin.prodhse.feature.venues.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.venues.data.remote.PlacesApi
import dev.maxsiomin.prodhse.feature.venues.data.remote.PlacesApiImpl
import dev.maxsiomin.prodhse.feature.venues.data.repository.PlacesRepositoryImpl
import dev.maxsiomin.prodhse.feature.venues.data.repository.PlansRepositoryImpl
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlansRepository

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class RepositoryAndNetworkModule {

    @Binds
    @ViewModelScoped
    abstract fun providePlacesApi(impl: PlacesApiImpl): PlacesApi

    @Binds
    @ViewModelScoped
    abstract fun providePlacesRepository(impl: PlacesRepositoryImpl): PlacesRepository

    @Binds
    @ViewModelScoped
    abstract fun providePlansRepository(impl: PlansRepositoryImpl): PlansRepository

}
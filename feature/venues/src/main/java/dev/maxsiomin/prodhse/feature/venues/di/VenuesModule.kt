package dev.maxsiomin.prodhse.feature.venues.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.venues.data.remote.PlacesApi
import dev.maxsiomin.prodhse.feature.venues.data.remote.PlacesApiImpl
import dev.maxsiomin.prodhse.feature.venues.data.repository.PlacesRepositoryImpl
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object VenuesModule {

    @Provides
    @ViewModelScoped
    fun providePlacesApi(impl: PlacesApiImpl): PlacesApi = impl
    
    @Provides
    @ViewModelScoped
    fun providePlacesRepository(impl: PlacesRepositoryImpl): PlacesRepository = impl

}
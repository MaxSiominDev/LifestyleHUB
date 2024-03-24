package dev.maxsiomin.prodhse.feature.venues.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.venues.data.local.PlansDao
import dev.maxsiomin.prodhse.feature.venues.data.local.PlansDatabase
import dev.maxsiomin.prodhse.feature.venues.data.remote.PlacesApi
import dev.maxsiomin.prodhse.feature.venues.data.remote.PlacesApiImpl
import dev.maxsiomin.prodhse.feature.venues.data.repository.PlacesRepositoryImpl
import dev.maxsiomin.prodhse.feature.venues.data.repository.PlansRepositoryImpl
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlacesRepository
import dev.maxsiomin.prodhse.feature.venues.domain.repository.PlansRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object VenuesModule {

    @Provides
    @ViewModelScoped
    fun providePlacesApi(impl: PlacesApiImpl): PlacesApi = impl
    
    @Provides
    @ViewModelScoped
    fun providePlacesRepository(impl: PlacesRepositoryImpl): PlacesRepository = impl

    @Provides
    @ViewModelScoped
    fun providePlansDatabase(@ApplicationContext context: Context): PlansDatabase {
        return Room.databaseBuilder(
            context,
            PlansDatabase::class.java,
            "plansDb",
        ).build()
    }

    @Provides
    @ViewModelScoped
    fun provideDao(database: PlansDatabase): PlansDao {
        return database.dao
    }

    @Provides
    @ViewModelScoped
    fun providePlansRepository(impl: PlansRepositoryImpl): PlansRepository = impl

}
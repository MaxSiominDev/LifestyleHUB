package dev.maxsiomin.prodhse.feature.home.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.home.data.local.PlansDao
import dev.maxsiomin.prodhse.feature.home.data.local.PlansDatabase

@Module
@InstallIn(ViewModelComponent::class)
internal object DatabaseModule {

    @Provides
    @ViewModelScoped
    fun providePlansDatabase(@ApplicationContext context: Context): PlansDatabase {
        return Room.databaseBuilder(
            context,
            PlansDatabase::class.java,
            PlansDatabase.NAME,
        ).build()
    }

    @Provides
    fun provideDao(database: PlansDatabase): PlansDao {
        return database.dao
    }

}
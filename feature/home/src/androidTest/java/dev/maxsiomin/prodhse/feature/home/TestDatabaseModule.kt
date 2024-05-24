package dev.maxsiomin.prodhse.feature.home

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.maxsiomin.prodhse.feature.home.data.local.PlansDao
import dev.maxsiomin.prodhse.feature.home.data.local.PlansDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideTestDatabase(): PlansDatabase {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlansDatabase::class.java,
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideDao(database: PlansDatabase): PlansDao {
        return database.dao
    }

}
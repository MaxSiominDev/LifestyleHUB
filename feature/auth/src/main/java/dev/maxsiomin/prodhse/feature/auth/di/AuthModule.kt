package dev.maxsiomin.prodhse.feature.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.auth.data.remote.RandomUserApi
import dev.maxsiomin.prodhse.feature.auth.data.remote.RandomUserApiImpl
import dev.maxsiomin.prodhse.feature.auth.data.repository.RandomUserRepositoryImpl
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomUserRepository

@InstallIn(ViewModelComponent::class)
@Module
object AuthModule {

    @ViewModelScoped
    @Provides
    fun provideRandomUserApi(impl: RandomUserApiImpl): RandomUserApi = impl

    @ViewModelScoped
    @Provides
    fun provideRandomUserRepository(impl: RandomUserRepositoryImpl): RandomUserRepository = impl

}
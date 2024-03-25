package dev.maxsiomin.prodhse.feature.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api.RandomActivityApi
import dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api.RandomActivityApiImpl
import dev.maxsiomin.prodhse.feature.auth.data.remote.nager.NagerApi
import dev.maxsiomin.prodhse.feature.auth.data.remote.nager.NagerApiImpl
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApi
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApiImpl
import dev.maxsiomin.prodhse.feature.auth.data.repository.NagerRepositoryImpl
import dev.maxsiomin.prodhse.feature.auth.data.repository.RandomActivityRepositoryImpl
import dev.maxsiomin.prodhse.feature.auth.data.repository.RandomUserRepositoryImpl
import dev.maxsiomin.prodhse.feature.auth.domain.repository.NagerRepository
import dev.maxsiomin.prodhse.feature.auth.domain.repository.RandomActivityRepository
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

    @ViewModelScoped
    @Provides
    fun provideRandomActivityApi(impl: RandomActivityApiImpl): RandomActivityApi = impl

    @ViewModelScoped
    @Provides
    fun provideRandomActivityRepository(impl: RandomActivityRepositoryImpl): RandomActivityRepository = impl

    @ViewModelScoped
    @Provides
    fun provideNagerApi(impl: NagerApiImpl): NagerApi = impl

    @ViewModelScoped
    @Provides
    fun provideNagerRepository(impl: NagerRepositoryImpl): NagerRepository = impl

}
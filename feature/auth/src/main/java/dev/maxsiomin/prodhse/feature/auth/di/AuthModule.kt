package dev.maxsiomin.prodhse.feature.auth.di

import dagger.Binds
import dagger.Module
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
abstract class AuthModule {

    @ViewModelScoped
    @Binds
    abstract fun provideRandomUserApi(impl: RandomUserApiImpl): RandomUserApi

    @ViewModelScoped
    @Binds
    abstract fun provideRandomUserRepository(impl: RandomUserRepositoryImpl): RandomUserRepository

    @ViewModelScoped
    @Binds
    abstract fun provideRandomActivityApi(impl: RandomActivityApiImpl): RandomActivityApi

    @ViewModelScoped
    @Binds
    abstract fun provideRandomActivityRepository(impl: RandomActivityRepositoryImpl): RandomActivityRepository

    @ViewModelScoped
    @Binds
    abstract fun provideNagerApi(impl: NagerApiImpl): NagerApi

    @ViewModelScoped
    @Binds
    abstract fun provideNagerRepository(impl: NagerRepositoryImpl): NagerRepository

}
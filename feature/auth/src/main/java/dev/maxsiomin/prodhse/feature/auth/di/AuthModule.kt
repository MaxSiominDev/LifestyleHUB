package dev.maxsiomin.prodhse.feature.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApi
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApiImpl
import dev.maxsiomin.prodhse.feature.auth.data.repository.RandomUserRepositoryImpl
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

}
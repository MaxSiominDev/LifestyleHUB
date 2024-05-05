package dev.maxsiomin.prodhse.feature.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.prodhse.feature.auth.data.auth.Authenticator
import dev.maxsiomin.prodhse.feature.auth.data.auth.AuthenticatorImpl
import dev.maxsiomin.prodhse.feature.auth.data.dto.random_user.Result
import dev.maxsiomin.prodhse.feature.auth.data.mappers.RandomUserMapper
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApi
import dev.maxsiomin.prodhse.feature.auth.data.remote.random_user.RandomUserApiImpl
import dev.maxsiomin.prodhse.feature.auth.data.repository.UsersRepositoryImpl
import dev.maxsiomin.prodhse.feature.auth.domain.model.RandomUserData
import dev.maxsiomin.prodhse.feature.auth.domain.repository.UsersRepository

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class AuthModule {

    @ViewModelScoped
    @Binds
    abstract fun provideRandomUserApi(impl: RandomUserApiImpl): RandomUserApi

    @ViewModelScoped
    @Binds
    abstract fun provideRandomUserRepository(impl: UsersRepositoryImpl): UsersRepository

    @ViewModelScoped
    @Binds
    abstract fun bindRandomUserMapper(impl: RandomUserMapper): ToDomainMapper<Result, RandomUserData>

    @ViewModelScoped
    @Binds
    abstract fun bindAuthenticator(impl: AuthenticatorImpl): Authenticator

}
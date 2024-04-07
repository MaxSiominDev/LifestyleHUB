package dev.maxsiomin.prodhse.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.maxsiomin.prodhse.core.location.PermissionChecker
import dev.maxsiomin.prodhse.core.location.PermissionCheckerImpl
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.core.util.LocaleManagerImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class CoreBindingModule {

    @Binds
    abstract fun provideLocaleManager(impl: LocaleManagerImpl): LocaleManager

}
package dev.maxsiomin.prodhse.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.prodhse.core.LocaleManager
import dev.maxsiomin.prodhse.core.LocaleManagerImpl
import dev.maxsiomin.prodhse.core.location.DefaultLocationClient
import dev.maxsiomin.prodhse.core.location.LocationClient
import dev.maxsiomin.prodhse.core.location.PermissionChecker
import dev.maxsiomin.prodhse.core.location.PermissionCheckerImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun providePermissionChecker(@ApplicationContext context: Context): PermissionChecker {
        return PermissionCheckerImpl(context)
    }

    @Singleton
    @Provides
    fun provideLocationClient(
        @ApplicationContext context: Context,
        client: FusedLocationProviderClient,
        permissionChecker: PermissionChecker,
    ): LocationClient {
        return DefaultLocationClient(context, client, permissionChecker)
    }

    @Provides
    fun provideLocaleManager(impl: LocaleManagerImpl): LocaleManager = impl

    @Provides
    fun provideAuthManager(): AuthManager = AuthManager.instance

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

}
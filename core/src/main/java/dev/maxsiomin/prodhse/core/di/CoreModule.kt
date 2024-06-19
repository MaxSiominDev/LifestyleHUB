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
import dev.maxsiomin.prodhse.core.location.DefaultLocationTracker
import dev.maxsiomin.prodhse.core.location.LocationTracker
import dev.maxsiomin.prodhse.core.location.PermissionChecker
import dev.maxsiomin.prodhse.core.location.PermissionCheckerImpl
import dev.maxsiomin.prodhse.core.util.DateFormatter
import dev.maxsiomin.prodhse.core.util.DefaultDateFormatter
import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.core.util.StandardDispatchers
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
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

    @Singleton
    @Provides
    fun provideLocationClient(
        @ApplicationContext context: Context,
        client: FusedLocationProviderClient,
    ): LocationTracker {
        return DefaultLocationTracker(context, client)
    }

    @Provides
    fun provideAuthManager(): AuthManager = AuthManager.instance

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    /** Was used for providing date formatting for different languages but I removed supporting any languages except English */
    fun provideDateFormatter(localeManager: LocaleManager): DateFormatter {
        return when (localeManager.getLocaleLanguage()) {

            LocaleManager.DEFAULT_LOCALE -> {
                DefaultDateFormatter()
            }

            else -> throw IllegalArgumentException("Locale is invalid")
        }
    }

    @Provides
    fun providePermissionChecker(@ApplicationContext context: Context): PermissionChecker {
        return PermissionCheckerImpl(context)
    }

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return StandardDispatchers
    }

}
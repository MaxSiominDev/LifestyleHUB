package dev.maxsiomin.prodhse

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.prodhse.core.isDebug
import kotlinx.coroutines.runBlocking
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AuthManager.init(this)

        if (isDebug()) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

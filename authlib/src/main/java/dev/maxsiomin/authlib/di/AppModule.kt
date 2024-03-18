package dev.maxsiomin.authlib.di

import android.content.SharedPreferences
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.data.room.UsersDao
import dev.maxsiomin.authlib.data.room.UsersDatabase
import dev.maxsiomin.authlib.domain.repository.UsersRepository

internal abstract class AppModule {

    abstract val usersDatabase: UsersDatabase

    abstract val usersDao: UsersDao

    abstract val usersRepo: UsersRepository

    abstract val sharedPrefs: SharedPreferences

    abstract val authManager: AuthManager

    companion object {
        lateinit var instance: AppModule
            private set
    }

    protected fun setInstance(newInstance: AppModule) {
        instance = newInstance
    }
}
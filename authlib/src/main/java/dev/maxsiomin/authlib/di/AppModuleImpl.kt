package dev.maxsiomin.authlib.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.data.repository.UsersRepositoryImpl
import dev.maxsiomin.authlib.data.room.UsersDao
import dev.maxsiomin.authlib.data.room.UsersDatabase
import dev.maxsiomin.authlib.domain.repository.UsersRepository

internal class AppModuleImpl private constructor(private val context: Context) : AppModule() {

    init {
        setInstance(this)
    }

    override val usersDatabase: UsersDatabase by lazy {
        Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "usersDb",
        ).build()
    }

    override val usersDao: UsersDao by lazy {
        usersDatabase.dao
    }

    override val sharedPrefs: SharedPreferences by lazy {
        val sharedPrefsName = "dev.maxsiomin.authlib-${context.packageName}-authlib"
        context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
    }

    override val usersRepo: UsersRepository by lazy {
        UsersRepositoryImpl(usersDao, sharedPrefs)
    }

    override val authManager: AuthManager by lazy {
        AuthManager(usersRepo)
    }

    companion object {
        fun init(context: Context) {
            AppModuleImpl(context)
        }
    }

}
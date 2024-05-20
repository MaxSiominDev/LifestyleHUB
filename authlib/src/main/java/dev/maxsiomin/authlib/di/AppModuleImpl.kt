package dev.maxsiomin.authlib.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dev.maxsiomin.authlib.AuthManager
import dev.maxsiomin.authlib.data.repository.UsersRepositoryImpl
import dev.maxsiomin.authlib.data.local.UsersDao
import dev.maxsiomin.authlib.data.local.UsersDatabase
import dev.maxsiomin.authlib.domain.repository.UsersRepository
import dev.maxsiomin.authlib.security.JvmStringHasher
import dev.maxsiomin.authlib.security.StringHasher

internal class AppModuleImpl private constructor(private val context: Context) : AppModule() {

    init {
        setInstance(this)
    }

    override val usersDatabase: UsersDatabase by lazy {
        Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            UsersDatabase.NAME,
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

    override val stringHasher: StringHasher by lazy {
        JvmStringHasher()
    }

    override val authManager: AuthManager = AuthManager(usersRepo, stringHasher)

    companion object {
        fun init(context: Context) {
            AppModuleImpl(context)
        }
    }

}
package dev.maxsiomin.authlib.data.repository

import android.content.SharedPreferences
import dev.maxsiomin.authlib.data.room.UserEntity
import dev.maxsiomin.authlib.data.room.UsersDao
import dev.maxsiomin.authlib.domain.repository.UsersRepository

internal class UsersRepositoryImpl(private val dao: UsersDao, private val prefs: SharedPreferences) :
    UsersRepository {

    override suspend fun getUserByName(username: String): UserEntity? {
        return dao.getUserByName(username)
    }

    override suspend fun getCurrentUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }

    private companion object {
        private const val KEY_USERNAME = "username"
    }

}
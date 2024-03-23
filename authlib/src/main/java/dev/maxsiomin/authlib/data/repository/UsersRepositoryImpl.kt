package dev.maxsiomin.authlib.data.repository

import android.annotation.SuppressLint
import android.content.SharedPreferences
import dev.maxsiomin.authlib.data.mappers.UserEntityToUserInfoMapper
import dev.maxsiomin.authlib.data.mappers.UserInfoToUserEntityMapper
import dev.maxsiomin.authlib.data.room.UsersDao
import dev.maxsiomin.authlib.domain.UserInfo
import dev.maxsiomin.authlib.domain.repository.UsersRepository

@SuppressLint("ApplySharedPref")
internal class UsersRepositoryImpl(
    private val dao: UsersDao,
    private val prefs: SharedPreferences
) : UsersRepository {

    override suspend fun getUserByName(username: String): UserInfo? {
        val entity = dao.getUserByName(username) ?: return null
        return UserEntityToUserInfoMapper().invoke(entity)
    }

    override suspend fun getCurrentUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }

    override suspend fun registerUser(userInfo: UserInfo) {
        val entity = UserInfoToUserEntityMapper().invoke(userInfo)
        dao.insertNewUser(entity)
    }

    override suspend fun login(username: String) {
        prefs.edit().apply {
            putString(KEY_USERNAME, username)
        }.commit()
    }

    override suspend fun logout() {
        prefs.edit().apply {
            remove(KEY_USERNAME)
        }.commit()
    }

    private companion object {
        private const val KEY_USERNAME = "username"
    }

}
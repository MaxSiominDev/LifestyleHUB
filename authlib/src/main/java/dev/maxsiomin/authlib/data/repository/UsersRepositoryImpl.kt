package dev.maxsiomin.authlib.data.repository

import android.annotation.SuppressLint
import android.content.SharedPreferences
import dev.maxsiomin.authlib.data.mappers.UserEntityToUserInfoMapper
import dev.maxsiomin.authlib.data.mappers.UserInfoToUserEntityMapper
import dev.maxsiomin.authlib.data.local.UsersDao
import dev.maxsiomin.authlib.domain.model.UserInfo
import dev.maxsiomin.authlib.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("ApplySharedPref")
internal class UsersRepositoryImpl(
    private val dao: UsersDao,
    private val prefs: SharedPreferences
) : UsersRepository {

    override suspend fun getUserByName(username: String): UserInfo? = withContext(Dispatchers.IO) {
        val entity = dao.getUserByName(username) ?: return@withContext null
        return@withContext UserEntityToUserInfoMapper().invoke(entity)
    }

    override suspend fun getCurrentUsername(): String? = withContext(Dispatchers.IO) {
        return@withContext prefs.getString(KEY_USERNAME, null)
    }

    override suspend fun registerUser(userInfo: UserInfo) = withContext(Dispatchers.IO) {
        val entity = UserInfoToUserEntityMapper().invoke(userInfo)
        dao.insertNewUser(entity)
    }

    override suspend fun login(username: String) = withContext(Dispatchers.IO) {
        prefs.edit().apply {
            putString(KEY_USERNAME, username)
        }.commit()
        Unit
    }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        prefs.edit().apply {
            remove(KEY_USERNAME)
        }.commit()
        Unit
    }

    private companion object {
        private const val KEY_USERNAME = "username"
    }

}
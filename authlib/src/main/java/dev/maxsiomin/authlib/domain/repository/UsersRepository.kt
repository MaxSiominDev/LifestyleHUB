package dev.maxsiomin.authlib.domain.repository

import dev.maxsiomin.authlib.domain.model.UserInfo

internal interface UsersRepository {

    suspend fun getUserByName(username: String): UserInfo?

    suspend fun getCurrentUsername(): String?

    suspend fun registerUser(userInfo: UserInfo)

    suspend fun login(username: String)

    suspend fun logout()

}
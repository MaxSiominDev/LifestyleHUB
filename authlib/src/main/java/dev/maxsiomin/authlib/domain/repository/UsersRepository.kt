package dev.maxsiomin.authlib.domain.repository

import dev.maxsiomin.authlib.data.room.UserEntity

internal interface UsersRepository {

    suspend fun getUserByName(username: String): UserEntity?

    suspend fun getCurrentUsername(): String?

}
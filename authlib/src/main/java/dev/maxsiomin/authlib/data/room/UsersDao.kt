package dev.maxsiomin.authlib.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
internal interface UsersDao {

    @Query(value = "SELECT * FROM USERS WHERE username=:username")
    suspend fun getUserByName(username: String): UserEntity?

}
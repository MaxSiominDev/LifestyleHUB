package dev.maxsiomin.authlib.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface UsersDao {

    @Query(value = "SELECT * FROM USERS WHERE username = :username")
    suspend fun getUserByName(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNewUser(userEntity: UserEntity)

}
package dev.maxsiomin.authlib.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
internal data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    val username: String,

    @ColumnInfo(name = "pwdHash")
    val passwordHash: String,

)

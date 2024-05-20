package dev.maxsiomin.authlib.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
internal data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    val username: String,

    @ColumnInfo(name = "pwdHash")
    val passwordHash: String,

    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @ColumnInfo(name = "fullName")
    val fullName: String,

)

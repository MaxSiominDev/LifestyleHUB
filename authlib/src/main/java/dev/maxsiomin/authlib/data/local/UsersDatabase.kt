package dev.maxsiomin.authlib.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 2)
internal abstract class UsersDatabase : RoomDatabase() {

    abstract val dao: UsersDao

    companion object {
        const val NAME = "usersDb"
    }

}

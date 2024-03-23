package dev.maxsiomin.authlib.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 2)
internal abstract class UsersDatabase : RoomDatabase() {

    abstract val dao: UsersDao

}

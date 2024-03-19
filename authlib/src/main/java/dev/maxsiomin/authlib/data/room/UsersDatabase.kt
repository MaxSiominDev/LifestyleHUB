package dev.maxsiomin.authlib.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
internal abstract class UsersDatabase : RoomDatabase() {

    abstract val dao: UsersDao

}

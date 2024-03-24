package dev.maxsiomin.prodhse.feature.venues.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlanEntity::class], version = 1)
abstract class PlansDatabase : RoomDatabase() {

    abstract val dao: PlansDao

}
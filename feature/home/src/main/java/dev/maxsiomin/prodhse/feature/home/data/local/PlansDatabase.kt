package dev.maxsiomin.prodhse.feature.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlanEntity::class], version = 1)
internal abstract class PlansDatabase : RoomDatabase() {

    abstract val dao: PlansDao

    companion object {
        const val NAME = "planDb"
    }

}
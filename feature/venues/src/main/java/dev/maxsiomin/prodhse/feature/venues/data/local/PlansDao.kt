package dev.maxsiomin.prodhse.feature.venues.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlansDao {

    @Insert
    suspend fun insertPlan(planEntity: PlanEntity)

    @Query("SELECT * FROM plans")
    suspend fun getPlans(): List<PlanEntity>

}
package dev.maxsiomin.prodhse.feature.venues.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
internal interface PlansDao {

    @Insert
    suspend fun insertPlan(planEntity: PlanEntity)

    /** I know about [Upsert] but decided not to use it here */
    @Update
    suspend fun updatePlan(planEntity: PlanEntity)

    @Query("SELECT * FROM plans")
    suspend fun getPlans(): List<PlanEntity>

    @Query("SELECT * FROM plans WHERE id=:id")
    suspend fun getPlanById(id: Long): PlanEntity?

}
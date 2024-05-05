package dev.maxsiomin.prodhse.feature.home.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface PlansDao {

    @Upsert
    suspend fun upsertPlan(planEntity: PlanEntity)

    @Query("SELECT * FROM plans")
    suspend fun getPlans(): List<PlanEntity>

    @Query("SELECT * FROM plans WHERE id = :id")
    suspend fun getPlanById(id: Long): PlanEntity?

    @Delete
    suspend fun deletePlan(plan: PlanEntity)

}
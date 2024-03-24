package dev.maxsiomin.prodhse.feature.venues.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
data class PlanEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo("fsqId")
    val placeId: String,

    @ColumnInfo("note")
    val note: String,

    @ColumnInfo("date")
    val date: Long,

)
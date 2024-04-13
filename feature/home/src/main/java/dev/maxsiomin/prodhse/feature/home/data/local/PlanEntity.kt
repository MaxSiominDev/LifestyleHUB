package dev.maxsiomin.prodhse.feature.home.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
internal data class PlanEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo("fsqId")
    val placeId: String,

    @ColumnInfo("noteTitle")
    val noteTitle: String,

    @ColumnInfo("noteText")
    val noteText: String,

    @ColumnInfo("date")
    val date: Long,

)
package com.example.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityTable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "time") val time: String?,

)

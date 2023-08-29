package com.example.weather

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityTable::class], version = 1) // Increase version number to 2
abstract class TimeDatabase : RoomDatabase() {
    abstract fun timeDao(): TimeDao


}
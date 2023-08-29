package com.example.weather

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Weather::class], version = 2) // Increase version number to 2
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao


}
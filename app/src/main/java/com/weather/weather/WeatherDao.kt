package com.example.weather

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert
    fun insertUser(weather: Weather)


    @Query("SELECT city FROM Weather WHERE city = :city1")
    fun getcity(city1: String): String?

    @Query("SELECT condition FROM Weather WHERE city = :city1")
    fun getc(city1: String): String?

    @Query("SELECT weather FROM Weather WHERE city = :city1")
    fun gettime(city1: String): Double?

    @Query("UPDATE Weather SET weather = :newweather WHERE city = :city1")
    fun updatetime(newweather: Double, city1: String)
}

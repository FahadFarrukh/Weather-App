package com.example.weather

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TimeDao {
    @Insert
    fun insertUser(cityTable: CityTable)



    @Query("SELECT city FROM CityTable WHERE city = :city1")
    fun getcity(city1: String): String?


    @Query("SELECT time FROM CityTable WHERE city = :city1")
    fun gettime(city1: String): String?

    @Query("UPDATE CityTable SET time = :newtime WHERE city = :city1")
    fun updatetime(newtime: String, city1: String)
}

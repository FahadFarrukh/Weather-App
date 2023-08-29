package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/api/timezone/{timezone}")
    fun getWorldTime(@Path("timezone") timezone: String): Call<WorldTimeData>
}

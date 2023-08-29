package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeApiService {
    @GET("/v1/current_time.json")
    fun getCurrentTime(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): Call<TimeApiResponse>
}

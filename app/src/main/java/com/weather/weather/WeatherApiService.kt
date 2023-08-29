package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/v1/current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): Call<WeatherApiResponse>

    @GET("/v1/forecast.json")
    fun get5DayForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int = 5
    ): Call<WeatherApiResponse>

    @GET("/v1/forecast.json")
    fun getHourlyForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("hourly") hourly: Int = 1
    ): Call<WeatherApiResponse>
    @GET("/v1/current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("aqi") aqi: String = "no"  // Optionally, include other query parameters
    ): Call<WeatherApiResponse>
    @GET("/v1/current_time.json") // Replace with the actual endpoint for fetching time
 // Replace with the actual endpoint for fetching time
    fun getCurrentTime(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): Call<TimeApiResponse>
}


















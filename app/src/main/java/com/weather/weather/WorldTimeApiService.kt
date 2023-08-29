package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WorldTimeApiService {
    @GET("{cityName}")
    fun getCityTime(@Path("cityName") cityName: String): Call<CityTimeResponse>
}

package com.example.weather

data class ForecastDay(
    val date: String,
    val day: DayForecast,
    val astro: Astro,
    val hour: List<HourlyForecast>
)


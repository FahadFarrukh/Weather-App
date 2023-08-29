package com.example.weather

data class LocationData(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Long,  // Add localtime_epoch property
    val localtime: String        // Add localtime property
)


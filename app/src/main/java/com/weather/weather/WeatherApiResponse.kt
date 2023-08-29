package com.example.weather

data class WeatherApiResponse(
    val location: LocationData,
    val current: CurrentWeatherData,
    val forecast: ForecastData,
    val forecastday: List<DayForecast>
)


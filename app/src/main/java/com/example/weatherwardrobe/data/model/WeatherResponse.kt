package com.example.weatherwardrobe.data.model

data class WeatherResponse(
    val request: RequestWeather,
    val location: Location,
    val current: Current
)

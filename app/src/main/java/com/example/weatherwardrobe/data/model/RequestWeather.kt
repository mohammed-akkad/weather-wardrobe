package com.example.weatherwardrobe.data.model

data class RequestWeather(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)

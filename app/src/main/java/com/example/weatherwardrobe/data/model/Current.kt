package com.example.weatherwardrobe.data.model

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("observation_time") val observationTime: String,
    val temperature: Int,
    @SerializedName("weather_icons") val weatherIcons: List<String>,
    @SerializedName("weather_descriptions") val weatherDescriptions: List<String>,
    @SerializedName("wind_speed") val windSpeed: Int,
    val visibility: Int,
    val pressure: Int
)

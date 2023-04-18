package com.example.weatherwardrobe.core.data.model

import com.google.gson.annotations.SerializedName

data class WeatherCurrent(
    @SerializedName("observation_time") val observationTime: String,
    @SerializedName("temperature") val temperature: Int,
    @SerializedName("weather_icons") val weatherIcons: List<String>,
    @SerializedName("weather_descriptions") val weatherDescriptions: List<String>,
    @SerializedName("wind_speed") val windSpeed: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("pressure") val pressure: Int
)

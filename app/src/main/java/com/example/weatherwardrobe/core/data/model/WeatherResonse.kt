package com.example.weatherwardrobe.core.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("request") val weatherParams: WeatherParams,
    val location: Location,
    @SerializedName("current") val weatherCurrent: WeatherCurrent
)

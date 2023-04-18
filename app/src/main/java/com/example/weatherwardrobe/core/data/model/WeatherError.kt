package com.example.weatherwardrobe.core.data.model

import com.google.gson.annotations.SerializedName

data class WeatherError(
    @SerializedName("error") val error: ErrorData,
    @SerializedName("success") val success: Boolean
)


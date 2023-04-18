package com.example.weatherwardrobe.core.data.model

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("observation_time") val code: Int,
    @SerializedName("observation_time") val type: String,
    @SerializedName("observation_time") val info: String
)
package com.example.weatherwardrobe.core.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("localtime") val localtime: String
)

package com.example.weatherwardrobe.core.data.model

import com.google.gson.annotations.SerializedName

data class WardrobeItem(
    @SerializedName("temperatureRange") val temperatureRange: IntRange,
    @SerializedName("wardrobeItems") val wardrobeItems: Map<Int,Int>,
    @SerializedName("tips") val tips : List<String>
)
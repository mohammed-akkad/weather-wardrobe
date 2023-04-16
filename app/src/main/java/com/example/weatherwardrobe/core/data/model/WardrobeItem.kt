package com.example.weatherwardrobe.core.data.model

data class WardrobeItem(
    val temperatureRange: IntRange,
    val wardrobeItems: Map<Int,Int>,
    val tips : List<String>
)
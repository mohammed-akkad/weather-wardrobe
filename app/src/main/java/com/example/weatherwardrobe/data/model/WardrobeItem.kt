package com.example.weatherwardrobe.data.model

data class WardrobeItem(
    val temperatureRange: IntRange,
    val wardrobeItems: Map<Int,Int>
)
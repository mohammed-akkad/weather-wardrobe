package com.example.weatherwardrobe.util

import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.data.model.WardrobeItem

fun listOfWinterItems(): List<WardrobeItem> {
    val winterWardrobeItem =
        mapOf(1 to R.drawable.jacket_one, 2 to R.drawable.jacket_two, 3 to R.drawable.jacket_three)
    val summerWardrobeItem =
        mapOf(1 to R.drawable.shirt_one, 2 to R.drawable.shirt_two, 3 to R.drawable.shirt_three)
    val winterTemperature = 0..15
    val summerTemperature = 16..50

    return listOf(
        WardrobeItem(winterTemperature, winterWardrobeItem),
        WardrobeItem(summerTemperature, summerWardrobeItem)
    )
}
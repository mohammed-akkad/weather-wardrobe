package com.example.weatherwardrobe.core.data.local

import android.content.Context
import com.example.weatherwardrobe.util.listOfWinterItems

class GetListOfWardrobeItem {

    fun execute(temperature: Int, context: Context): Map<Int, Int>? {
        val wardrobeItems = listOfWinterItems(context)
        return wardrobeItems.firstOrNull { temperature in it.temperatureRange }?.wardrobeItems
    }
}
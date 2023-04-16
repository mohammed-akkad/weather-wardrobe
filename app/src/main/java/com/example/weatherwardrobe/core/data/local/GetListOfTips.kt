package com.example.weatherwardrobe.core.data.local

import android.content.Context
import com.example.weatherwardrobe.util.listOfWinterItems

class GetListOfTips {
    fun execute(temperature: Int, context: Context): List<String>? {
        val wardrobeItems = listOfWinterItems(context)
        return wardrobeItems.firstOrNull { temperature in it.temperatureRange }?.tips
    }
}
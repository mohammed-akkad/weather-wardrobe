package com.example.weatherwardrobe.data.interactor

import com.example.weatherwardrobe.util.listOfWinterItems

class GetListOfWardrobeItemInteractor {

    fun execute(temperature: Int): Map<Int, Int>? {
        val wardrobeItems = listOfWinterItems()
        return wardrobeItems.firstOrNull { temperature in it.temperatureRange }?.wardrobeItems
    }
}
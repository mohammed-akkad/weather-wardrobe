package com.example.weatherwardrobe.ui.screen.home

import com.example.weatherwardrobe.core.data.model.WeatherResponse

interface HomeView {
    fun onSuccess(response: WeatherResponse)
    fun onFailure(message: String?)
}
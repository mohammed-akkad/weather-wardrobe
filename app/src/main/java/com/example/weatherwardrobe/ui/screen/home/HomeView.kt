package com.example.weatherwardrobe.ui.screen.home

import com.example.weatherwardrobe.core.data.model.WeatherResponse

interface HomeView {
    fun showWeather(response: WeatherResponse)
    fun showError(message: String?)

    fun navigationToWelcomeFragment()

    fun setImageResource(imageId: Int)

    fun showProgressbarLoading()

    fun showTips(tips: List<String>?)
}
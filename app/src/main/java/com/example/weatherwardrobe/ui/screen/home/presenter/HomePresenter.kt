package com.example.weatherwardrobe.ui.screen.home.presenter

import com.example.weatherwardrobe.core.data.model.WeatherResponse
import com.example.weatherwardrobe.core.data.network.services.WeatherServices
import com.example.weatherwardrobe.ui.screen.home.HomeView
import com.google.gson.Gson

class HomePresenter {
    lateinit var homeView: HomeView

    fun makeRequest(country: String){
        val weather = WeatherServices()
        weather.getWeatherDataByCountry(
            onSuccess = {
                val body = it.body?.string().toString()
                val weatherResponse = Gson().fromJson(body, WeatherResponse::class.java)
                homeView.onSuccess(weatherResponse)
            },
            onFailure = {
                homeView.onFailure(it)
            },
            country = country
        )
    }
}
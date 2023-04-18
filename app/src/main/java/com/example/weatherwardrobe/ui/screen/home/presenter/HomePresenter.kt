package com.example.weatherwardrobe.ui.screen.home.presenter

import android.content.Context
import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.core.data.local.PrefsUtil
import com.example.weatherwardrobe.core.data.model.WardrobeItem
import com.example.weatherwardrobe.core.data.model.WeatherResponse
import com.example.weatherwardrobe.core.data.network.services.WeatherServices
import com.example.weatherwardrobe.ui.screen.home.HomeView
import com.google.gson.Gson

class HomePresenter {
    lateinit var homeView: HomeView

    fun loadCountryWeatherData() {
        val nameCountry = PrefsUtil.countryName
        if (nameCountry == null) {
            homeView.navigationToWelcomeFragment()
        } else {
            getWeather(nameCountry)
            homeView.showProgressbarLoading()
            loadWardrobeImage()
        }
    }

    fun deleteDataFromLocal() = PrefsUtil.deleteAllSharedPreferences()

    fun setTipsText(temperature: Int, requireContext: Context) {
        val tips = getListOfTips(temperature, requireContext)
        homeView.showTips(tips)
    }

    fun setImageFromWardrobe(response: WeatherResponse, requireContext: Context) {
        val networkCurrentDate = getNetworkDate()
        storeWeatherLocalDate(response)
        val localDate = getLocalDate()
        val imageItem = getRandomWardrobeItemImage(response, requireContext)
        val shouldUpdateImage = localDate != networkCurrentDate || getImageFromLocal() == 0
        if (shouldUpdateImage) {
            updateWardrobeImage(imageItem, response)
        } else {
            loadWardrobeImage()
        }
    }

    fun getWeather(country: String) {
        val weather = WeatherServices()
        weather.getWeatherDataByCountry(
            onSuccess = {
                val body = it.body?.string().toString()
                val weatherResponse = Gson().fromJson(body, WeatherResponse::class.java)
                homeView.showWeather(weatherResponse)
            },
            onFailure = {
                homeView.showError(it)
            },
            country = country
        )
    }

    private fun listOfWinterItems(context: Context): List<WardrobeItem> {
        val winterWardrobeItem =
            mapOf(1 to R.drawable.jacket_one, 2 to R.drawable.jacket_two, 3 to R.drawable.jacket_three)
        val summerWardrobeItem =
            mapOf(1 to R.drawable.shirt_one, 2 to R.drawable.shirt_two, 3 to R.drawable.shirt_three)
        val winterTemperature = 0..15
        val summerTemperature = 16..50
        val winterTips = listOf(
            context.getString(R.string.winterTipsOne),
            context.getString(R.string.winterTipsTwo),
            context.getString(R.string.winterTipsThree),
            context.getString(R.string.winterTipsFour),
            context.getString(R.string.winterTipsFive),
            context.getString(R.string.winterTipsSix),
            context.getString(R.string.winterTipsSeven)
        )
        val summerTips = listOf(
            context.getString(R.string.summerTipsOne),
            context.getString(R.string.summerTipsTwo),
            context.getString(R.string.summerTipsThree),
            context.getString(R.string.summerTipsFour),
            context.getString(R.string.summerTipsFive),
            context.getString(R.string.summerTipsSix),
            context.getString(R.string.summerTipsSeven)
        )

        return listOf(
            WardrobeItem(winterTemperature, winterWardrobeItem, winterTips),
            WardrobeItem(summerTemperature, summerWardrobeItem, summerTips)
        )
    }

    private fun getListOfTips(temperature: Int, context: Context): List<String>? {
        val wardrobeItems = listOfWinterItems(context)
        return wardrobeItems.firstOrNull { temperature in it.temperatureRange }?.tips
    }

    private fun getListOfWardrobeItem(temperature: Int, context: Context): Map<Int, Int>? {
        val wardrobeItems = listOfWinterItems(context)
        return wardrobeItems.firstOrNull { temperature in it.temperatureRange }?.wardrobeItems
    }

    private fun getRandomWardrobeItemImage(
        response: WeatherResponse,
        requireContext: Context
    ): Int {
        val mapOfImage = getListOfWardrobeItem(response.weatherCurrent.temperature, requireContext)
        return mapOfImage!!.values.random()
    }

    private fun storeWeatherLocalDate(response: WeatherResponse) {
        val currentLocalDate = response.location.localtime.substring(5..9)
        PrefsUtil.lastLocalDate = currentLocalDate
    }

    private fun updateWardrobeImage(imageItem: Int?, response: WeatherResponse) {
        imageItem?.let { image ->
            homeView.setImageResource(imageItem)
            PrefsUtil.imageIdWardrobe = image
            storeWeatherLocalDate(response)
        }
    }

    private fun loadWardrobeImage() {
        val imageId = getImageFromLocal()
        if (imageId != null) {
            homeView.setImageResource(imageId)
        }
    }

    private fun getNetworkDate() = PrefsUtil.getNetworkDate()

    private fun getImageFromLocal() = PrefsUtil.imageIdWardrobe

    private fun getLocalDate() = PrefsUtil.lastLocalDate

}
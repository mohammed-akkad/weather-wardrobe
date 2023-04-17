package com.example.weatherwardrobe.ui.screen.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.core.data.local.GetListOfTips
import com.example.weatherwardrobe.core.data.local.GetListOfWardrobeItem
import com.example.weatherwardrobe.core.data.model.WeatherResponse
import com.example.weatherwardrobe.ui.base.BaseFragment
import com.example.weatherwardrobe.core.data.local.PrefsUtil
import com.example.weatherwardrobe.databinding.FragmentHomeBinding
import com.example.weatherwardrobe.ui.screen.home.presenter.HomePresenter
import com.example.weatherwardrobe.ui.screen.welcome.WelcomeFragment
import com.example.weatherwardrobe.core.util.hide
import com.example.weatherwardrobe.core.util.show
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.weatherwardrobe.core.util.changeFragment
import com.example.weatherwardrobe.core.util.loadImageWithGlide
import com.example.weatherwardrobe.core.util.showSnackbar


class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeView {
    private val homePresenter = HomePresenter()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        homePresenter.homeView = this
        addCallBacks()
    }

    private fun addCallBacks() {
        deleteCardWeather()
        loadCountryWeatherData()
    }

    private fun deleteCardWeather() {
        binding.imageButtonDelete.setOnClickListener {
            PrefsUtil.deleteAllSharedPreferences()
            changeFragment(WelcomeFragment())
        }
    }

    private fun loadCountryWeatherData() {
        val nameCountry = PrefsUtil.countryName
        if (nameCountry == null) {
            changeFragment(WelcomeFragment())
        } else {
            makeWeatherRequest(nameCountry)
            loadWardrobeImage()
        }
    }

    private fun makeWeatherRequest(country: String) {
        when {
            country.isEmpty() -> toggleWeatherDetailsUIElements(View.GONE)
            country.isNotEmpty() -> {
                homePresenter.makeRequest(country)
                binding.progressBarLoading.show()
            }
        }
    }

    private fun getDataFromJson(response: WeatherResponse) {
        try {
            setWeatherDetails(response)
        } catch (e: NullPointerException) {
            changeFragment(WelcomeFragment())
            binding.root.showSnackbar(R.string.please_write_the_country_correctly)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setWeatherDetails(response: WeatherResponse) {
        binding.apply {
            response.apply {
                val locationText = "${location.name} - ${location.country}"
                textViewCountry.text = locationText
                textViewTemperature.text = getString(R.string.temperature, weatherCurrent.temperature)
                textViewWeatherDescriptions.text = weatherCurrent.weatherDescriptions.joinToString()
                textViewWind.text = getString(R.string.wind, weatherCurrent.windSpeed)
                textViewVisibility.text = getString(R.string.visibility, weatherCurrent.visibility)
                textViewPressure.text = getString(R.string.pressure, weatherCurrent.pressure)
                shapeImageViewWeather.loadImageWithGlide(weatherCurrent.weatherIcons.joinToString())
                setTipsText(weatherCurrent.temperature)
                setImageFromWardrobe(response)
            }
        }
    }

    private fun setTipsText(temperature: Int) {
        val tips = GetListOfTips().execute(temperature, requireContext())
        binding.textViewTips.text = tips?.randomOrNull()
    }

    private fun loadWardrobeImage() {
        val imageId = getImageFromLocal()
        if (imageId != null) {
            binding.imageViewWardrobe.setImageResource(imageId)
        }
    }

    private fun setImageFromWardrobe(response: WeatherResponse) {
        val networkCurrentDate = PrefsUtil.getNetworkDate()
        storeWeatherLocalDate(response)
        val localDate = getLocalDate()
        val imageItem = getRandomWardrobeItemImage(response)
        val shouldUpdateImage = localDate != networkCurrentDate || getImageFromLocal() == 0
        if (shouldUpdateImage) {
            updateWardrobeImage(imageItem, response)
        } else {
            loadWardrobeImage()
        }
    }

    private fun storeWeatherLocalDate(response: WeatherResponse) {
        val currentLocalDate = response.location.localtime.substring(5..9)
        PrefsUtil.lastLocalDate = currentLocalDate
    }

    private fun getLocalDate() = PrefsUtil.lastLocalDate

    private fun getImageFromLocal() = PrefsUtil.imageIdWardrobe

    private fun getRandomWardrobeItemImage(response: WeatherResponse): Int {
        val getListOfWardrobeItemInteractor = GetListOfWardrobeItem()
        val mapOfImage = getListOfWardrobeItemInteractor.execute(response.weatherCurrent.temperature, requireContext())
        return mapOfImage!!.values.random()
    }

    private fun updateWardrobeImage(imageItem: Int?, response: WeatherResponse) {
        imageItem?.let { image ->
            binding.imageViewWardrobe.setImageResource(imageItem)
            PrefsUtil.imageIdWardrobe = image
            storeWeatherLocalDate(response)
        }
    }

    private fun toggleWeatherDetailsUIElements(showDetails: Int) {
        binding.apply {
            cardViewItemWeather.visibility = showDetails
            imageViewWardrobe.visibility = showDetails
            textViewRecommendedClothing.visibility = showDetails
            textViewTips.visibility = showDetails
        }
    }

    override fun onSuccess(response: WeatherResponse) {
            activity?.runOnUiThread {
                binding.animationViewNoConnection.hide()
                binding.progressBarLoading.hide()
                getDataFromJson(response)
                toggleWeatherDetailsUIElements(View.VISIBLE)
            }
    }

    override fun onFailure(message: String?) {
        activity?.runOnUiThread {
            toggleWeatherDetailsUIElements(View.GONE)
            binding.animationViewNoConnection.show()
            binding.textViewTryAgain.apply {
                this.show()
                setOnClickListener { textViewTryAgain ->
                    binding.progressBarLoading.show()
                    PrefsUtil.countryName?.let { homePresenter.makeRequest(it) }
                    textViewTryAgain.hide()
                    binding.animationViewNoConnection.hide()
                }
            }
            binding.progressBarLoading.hide()
        }
    }
}
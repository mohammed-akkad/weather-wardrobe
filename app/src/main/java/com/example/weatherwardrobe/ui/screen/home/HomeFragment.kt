package com.example.weatherwardrobe.ui.screen.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.core.data.model.WeatherResponse
import com.example.weatherwardrobe.ui.base.BaseFragment
import com.example.weatherwardrobe.core.data.local.PrefsUtil
import com.example.weatherwardrobe.databinding.FragmentHomeBinding
import com.example.weatherwardrobe.ui.screen.home.presenter.HomePresenter
import com.example.weatherwardrobe.ui.screen.welcome.WelcomeFragment
import com.example.weatherwardrobe.core.util.hide
import com.example.weatherwardrobe.core.util.show
import com.example.weatherwardrobe.core.util.changeFragment
import com.example.weatherwardrobe.core.util.loadImageWithGlide
import com.example.weatherwardrobe.core.util.showSnackbar


class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeView {
    private val homePresenter = HomePresenter()

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePresenter.homeView = this
        addCallBacks()
    }

    private fun addCallBacks() {
        homePresenter.loadCountryWeatherData()
        binding.buttonChangeCountry.show()
        deleteCardWeather()
    }

    private fun deleteCardWeather() {
        binding.buttonChangeCountry.setOnClickListener {
            homePresenter.deleteDataFromLocal()
            changeFragment(WelcomeFragment())
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
                homePresenter.setTipsText(weatherCurrent.temperature, requireContext())
                homePresenter.setImageFromWardrobe(response, requireContext())
            }
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

    override fun showWeather(response: WeatherResponse) {
        activity?.runOnUiThread {
            binding.animationViewNoConnection.hide()
            binding.progressBarLoading.hide()
            getDataFromJson(response)
            toggleWeatherDetailsUIElements(View.VISIBLE)
        }
    }

    override fun showError(message: String?) {
        activity?.runOnUiThread {
            toggleWeatherDetailsUIElements(View.GONE)
            binding.animationViewNoConnection.show()
            setTextViewTryAgainClickListener()
            binding.progressBarLoading.hide()
        }
    }

    private fun setTextViewTryAgainClickListener() {
        binding.textViewTryAgain.apply {
            this.show()
            setOnClickListener { textViewTryAgain ->
                showProgressbarLoading()
                PrefsUtil.countryName?.let { homePresenter.getWeather(it) }
                textViewTryAgain.hide()
                binding.animationViewNoConnection.hide()
            }
        }
    }

    override fun navigationToWelcomeFragment() {
        changeFragment(WelcomeFragment())
    }

    override fun setImageResource(imageId: Int) {
        binding.imageViewWardrobe.setImageResource(imageId)
    }

    override fun showProgressbarLoading() {
        binding.progressBarLoading.show()
    }

    override fun showTips(tips: List<String>?) {
        binding.textViewTips.text = tips?.randomOrNull()
    }
}
package com.example.weatherwardrobe.ui.screen.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherwardrobe.BuildConfig
import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.data.interactor.GetListOfWardrobeItemInteractor
import com.example.weatherwardrobe.data.model.WeatherResponse
import com.example.weatherwardrobe.ui.MainActivity
import com.example.weatherwardrobe.ui.base.BaseFragment
import com.example.weatherwardrobe.util.MyInterCaptor
import com.example.weatherwardrobe.data.local.PrefsUtil
import com.example.weatherwardrobe.databinding.FragmentHomeBinding
import com.example.weatherwardrobe.util.glide
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.*
import java.io.IOException
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate
    private val client = OkHttpClient().newBuilder().addInterceptor(MyInterCaptor()).build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
    }

    private fun addCallBacks() {
        validation("palestine")
    }

    private fun validation(country: String) {
        when {
            country.isEmpty() -> {
                toggleWeatherDetailsUIElements(View.GONE)
            }
            country.isNotEmpty() -> {
                makeRequestWithOKHTTP(country)
                toggleWeatherDetailsUIElements(View.VISIBLE)
            }
        }
    }

    private fun makeRequestWithOKHTTP(country: String){
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("api.weatherstack.com")
            .addPathSegments("current")
            .addQueryParameter("access_key", BuildConfig.API_KEY)
            .addQueryParameter("query", "$country ")
            .build()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                (activity as MainActivity).runOnUiThread {
                    toggleWeatherDetailsUIElements(View.GONE)
                    binding.animationViewNoConnection.visibility = View.VISIBLE
                    binding.progressBarLoading.visibility = View.GONE
                    Log.d("MAIN_ACTIVITY", "${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    try {
                        val result = Gson().fromJson(jsonString, WeatherResponse::class.java)
                        (activity as MainActivity).runOnUiThread {
                            binding.animationViewNoConnection.visibility = View.GONE
                            binding.progressBarLoading.visibility = View.GONE
                            getDataFromJson(result)
                        }
                    } catch (e: JsonSyntaxException) {
//                        binding.itemCardWeather.isVisible = false
                        Log.d("MAIN_ACTIVITY", e.message.toString())
                    }
                }
            }
        })
    }
    private fun getDataFromJson(result: WeatherResponse) {
        try {
            getTextStringFromJson(result)
            getImageWeatherFromJson(result)
            setImageFromWardrobe(result)
        } catch (e: NullPointerException) {
            toggleWeatherDetailsUIElements(View.GONE)
            Log.d("MAIN_ACTIVITY", e.message.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getTextStringFromJson(result: WeatherResponse) {
        binding.apply {
            result.apply {
                textViewCountry.text = "${location.name} - ${location.country}"
                textViewTemperature.text = getString(R.string.temperature, current.temperature)
                textViewWeatherDescriptions.text = current.weatherDescriptions.joinToString()
                textViewWind.text = getString(R.string.wind, current.windSpeed)
                textViewVisibility.text = getString(R.string.visibility, current.visibility)
                textViewPressure.text = getString(R.string.pressure, current.pressure)
            }
        }
    }

    private fun getImageWeatherFromJson(result: WeatherResponse) {
        binding.apply {
            result.apply {
                glide(root.context, current.weatherIcons.joinToString(), shapeImageViewWeather)
            }
        }
    }
    private fun loadImage() {
        val imageId = PrefsUtil.imageIdWardrobe
        if(imageId != null && imageId != 0){
            binding.imageViewWardrobe.setImageResource(imageId)
        }
    }

    private fun setImageFromWardrobe(result: WeatherResponse) {
        val currentLocalDate = result.location.localtime.substring(5..9)
        val storedLocalDate = PrefsUtil.lastLocalDate
        val getListOfWardrobeItemInteractor = GetListOfWardrobeItemInteractor()
        val mapOfImage = getListOfWardrobeItemInteractor.execute(result.current.temperature)
        val imageItem = mapOfImage?.values?.randomOrNull()
        if (currentLocalDate != storedLocalDate) {
            imageItem?.let {
                binding.imageViewWardrobe.setImageResource(it)
                PrefsUtil.imageIdWardrobe = it
                PrefsUtil.lastLocalDate = PrefsUtil.getCurrentLocalDate()
            }
        }else{
            loadImage()
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
}
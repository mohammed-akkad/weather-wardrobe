package com.example.weatherwardrobe.core.data.network.services

import com.example.weatherwardrobe.core.data.network.services.util.Util.getUrl
import com.example.weatherwardrobe.core.data.network.services.base.BaseService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class WeatherServices : BaseService() {
    override val client: OkHttpClient by lazy {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient().newBuilder().addInterceptor(logInterceptor).build()
    }

    fun getWeatherDataByCountry(
        onFailure: (message: String?) -> Unit,
        onSuccess: (response: Response) -> Unit,
        country: String
    ){
        val request = Request.Builder()
            .url(getUrl(country)).build()
        makeHttpRequest(request,onFailure,onSuccess)
    }
}
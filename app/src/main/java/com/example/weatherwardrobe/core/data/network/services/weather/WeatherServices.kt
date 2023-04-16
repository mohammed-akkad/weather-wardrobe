package com.example.weatherwardrobe.core.data.network.services.weather

import com.example.weatherwardrobe.core.data.network.interceptors.WeatherInterceptors
import com.example.weatherwardrobe.core.data.network.services.util.Util.getUrl
import com.example.weatherwardrobe.core.data.network.services.weather.base.BaseService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class WeatherServices : BaseService() {
    override val client: OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(WeatherInterceptors()).build()

    fun weather(
        onFailure: (message: String?) -> Unit,
        onSuccess: (response: Response) -> Unit,
        country: String
    ){
        val request = Request.Builder()
            .url(getUrl(country)).build()
        call(request,onFailure,onSuccess)
    }
}
package com.example.weatherwardrobe.core.data.network.services.util

import com.example.weatherwardrobe.BuildConfig
import okhttp3.HttpUrl

object Util {
    fun getUrl(country: String): HttpUrl{
        return HttpUrl.Builder()
            .scheme("http")
            .host("api.weatherstack.com")
            .addPathSegments("current")
            .addQueryParameter("access_key", BuildConfig.API_KEY)
            .addQueryParameter("query", "$country ")
            .build()
    }
}
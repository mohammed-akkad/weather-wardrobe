package com.example.weatherwardrobe.core.data.network.services.util

import com.example.weatherwardrobe.BuildConfig
import okhttp3.HttpUrl

object Util {
    private const val API_SCHEME = "http"
    private const val API_HOST = "api.weatherstack.com"
    private const val API_PATH = "current"
    private const val API_ACCESS_KEY_PARAM = "access_key"
    private const val API_ACCESS_KEY_VALUE = BuildConfig.API_KEY
    private const val API_QUERY_PARAM = "query"
    fun getUrl(country: String): HttpUrl{
        return HttpUrl.Builder()
            .scheme(API_SCHEME)
            .host(API_HOST)
            .addPathSegments(API_PATH)
            .addQueryParameter(API_ACCESS_KEY_PARAM, API_ACCESS_KEY_VALUE)
            .addQueryParameter(API_QUERY_PARAM, "$country ")
            .build()
    }
}
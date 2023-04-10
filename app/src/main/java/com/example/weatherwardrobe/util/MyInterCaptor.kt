package com.example.weatherwardrobe.util

import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class MyInterCaptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val mRequest = chain.request().newBuilder()
        val mResponse = chain.proceed(mRequest.build())
        Log.d("123123123",mResponse.code.toString())
        return mResponse
    }
}
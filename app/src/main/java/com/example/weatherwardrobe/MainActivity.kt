package com.example.weatherwardrobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherwardrobe.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addCallBacks()

    }

    private fun addCallBacks() {
        binding.buttonGetData.setOnClickListener {
            makeRequestWithOKHTTP()
        }
    }

    private fun makeRequestWithOKHTTP() {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("api.weatherstack.com")
            .addPathSegments("current")
            .addQueryParameter("access_key", "6c499a638cccf2e9292fa288a60f8b10")
            .addQueryParameter("query", "palestine")
            .build()

        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(MainActivity::class.java.name, "${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    binding.textData.text = response.body?.string().toString()
                }
            }

        })


    }
}
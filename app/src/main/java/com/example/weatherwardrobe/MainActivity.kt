package com.example.weatherwardrobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val request = Request.Builder()
            .url("https://api.tomorrow.io/v4/timelines?apikey=i7fDcivw9N9Rm0CPUZjCKnBolxCfMnnM&location=40.75872069597532,-73.98529171943665&fields=temperature&timesteps=1h&units=metric")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

                runOnUiThread {
                    binding.textData.text = response.body?.string().toString()
                }
            }

        })


    }
}
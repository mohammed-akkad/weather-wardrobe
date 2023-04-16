package com.example.weatherwardrobe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.core.data.local.PrefsUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PrefsUtil.initPrefUtil(applicationContext)
    }
}
package com.example.weatherwardrobe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.databinding.ActivityMainBinding
import com.example.weatherwardrobe.ui.screen.home.HomeFragment
import com.example.weatherwardrobe.data.local.PrefsUtil


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addCallBacks()

    }

    private fun addCallBacks() {
        PrefsUtil.initPrefUtil(applicationContext)
        changeFragment(HomeFragment())
    }

    fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view,fragment)
            addToBackStack(null)
            commit()
        }
    }

}
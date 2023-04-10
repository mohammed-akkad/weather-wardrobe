package com.example.weatherwardrobe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherwardrobe.databinding.ActivityMainBinding
import com.example.weatherwardrobe.ui.screen.home.HomeFragment
import com.example.weatherwardrobe.ui.screen.search.SearchFragment
import com.example.weatherwardrobe.ui.screen.search.adapter.PagerAdapter
import com.example.weatherwardrobe.data.local.PrefsUtil
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val searchFragment = SearchFragment()
    private val homeFragment = HomeFragment()
    private val fragments = listOf(homeFragment,searchFragment)
    private val tabTitles = listOf("Weather", "Search", "Add")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addCallBacks()
        initViewPager()
        initTabLayout()
    }

    private fun addCallBacks() {
        PrefsUtil.initPrefUtil(applicationContext)
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun initViewPager() {
        val pagerAdapter = PagerAdapter(this, fragments)
        binding.viewPager.adapter = pagerAdapter
    }

}
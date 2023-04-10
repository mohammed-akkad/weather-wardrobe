package com.example.weatherwardrobe.ui.screen.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(container: FragmentActivity, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(container) {
    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}
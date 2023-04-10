package com.example.weatherwardrobe.ui.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherwardrobe.databinding.FragmentHomeBinding
import com.example.weatherwardrobe.ui.base.BaseFragment
import com.example.weatherwardrobe.data.local.PrefsUtil

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onStart() {
        super.onStart()
        loadImage()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallbacks()
    }

    private fun addCallbacks() {
    }

    private fun loadImage() {
        val imageId = PrefsUtil.imageIdWardrobe
        if(imageId != null && imageId != 0){
            binding.imageView.setImageResource(imageId)
            binding.imageView.setOnClickListener {
                Log.d("HOME_FRAGMENT","load image: $imageId")
            }
        }
    }

}
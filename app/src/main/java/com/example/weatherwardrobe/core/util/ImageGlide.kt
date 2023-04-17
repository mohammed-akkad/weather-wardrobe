package com.example.weatherwardrobe.core.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageWithGlide(url: String){
    Glide.with(this).load(url).into(this)
}
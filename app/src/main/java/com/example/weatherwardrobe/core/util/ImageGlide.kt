package com.example.weatherwardrobe.core.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun glide(context: Context, url: String, imageView: ImageView) {
    Glide.with(context).load(url).into(imageView)
}
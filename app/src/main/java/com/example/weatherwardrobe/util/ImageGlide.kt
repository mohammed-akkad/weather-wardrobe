package com.example.weatherwardrobe.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

fun glide(context: Context, url: String, imageView: ImageView) {
    Glide
        .with(context)
        .load(url)
        .into(imageView)
}
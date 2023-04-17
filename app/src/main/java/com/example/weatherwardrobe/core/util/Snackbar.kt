package com.example.weatherwardrobe.core.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(messageResId: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, messageResId, duration).show()
}
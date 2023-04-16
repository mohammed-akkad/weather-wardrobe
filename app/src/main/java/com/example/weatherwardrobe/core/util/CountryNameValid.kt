package com.example.weatherwardrobe.core.util

fun String.isCountryNameValid(): Boolean {
    val regex = Regex("[a-zA-Z]+")
    return this.matches(regex)
}
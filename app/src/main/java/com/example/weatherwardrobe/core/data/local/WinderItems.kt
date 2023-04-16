package com.example.weatherwardrobe.util

import android.content.Context
import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.core.data.model.WardrobeItem

fun listOfWinterItems(context: Context): List<WardrobeItem> {
    val winterWardrobeItem =
        mapOf(1 to R.drawable.jacket_one, 2 to R.drawable.jacket_two, 3 to R.drawable.jacket_three)
    val summerWardrobeItem =
        mapOf(1 to R.drawable.shirt_one, 2 to R.drawable.shirt_two, 3 to R.drawable.shirt_three)
    val winterTemperature = 0..15
    val summerTemperature = 16..50
    val winterTips = listOf(
        context.getString(R.string.winterTipsOne),
        context.getString(R.string.winterTipsTwo),
        context.getString(R.string.winterTipsThree),
        context.getString(R.string.winterTipsFour),
        context.getString(R.string.winterTipsFive),
        context.getString(R.string.winterTipsSix),
        context.getString(R.string.winterTipsSeven)
    )
    val summerTips = listOf(
        context.getString(R.string.summerTipsOne),
        context.getString(R.string.summerTipsTwo),
        context.getString(R.string.summerTipsThree),
        context.getString(R.string.summerTipsFour),
        context.getString(R.string.summerTipsFive),
        context.getString(R.string.summerTipsSix),
        context.getString(R.string.summerTipsSeven)
    )

    return listOf(
        WardrobeItem(winterTemperature, winterWardrobeItem, winterTips),
        WardrobeItem(summerTemperature, summerWardrobeItem, summerTips)
    )
}
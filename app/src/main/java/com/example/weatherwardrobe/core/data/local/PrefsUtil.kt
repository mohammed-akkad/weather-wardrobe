package com.example.weatherwardrobe.core.data.local

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

object PrefsUtil {
    private var sharedPreferences: SharedPreferences? = null
    private const val IMAGE_ID_KEY = "IMAGE_ID_KEY"
    private const val SAVE_IMAGE_FROM_WARDROBE = "IMAGE"
    private const val LAST_LOCAL_DATE_KEY = "LAST_LOCAL_DATE_KEY"
    private const val COUNTRY_NAME_KEY = "COUNTRY_NAME_KEY"

    fun initPrefUtil(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            SAVE_IMAGE_FROM_WARDROBE,
            Context.MODE_PRIVATE
        )
    }

    var imageIdWardrobe: Int?
        get() = sharedPreferences?.getInt(IMAGE_ID_KEY, 0)
        set(value) {
            value?.let { sharedPreferences?.edit()?.putInt(IMAGE_ID_KEY, it) }?.apply()
        }

    var lastLocalDate: String?
        get() = sharedPreferences?.getString(LAST_LOCAL_DATE_KEY, null)
        set(value) {
            value?.let { sharedPreferences?.edit()?.putString(LAST_LOCAL_DATE_KEY, it) }?.apply()
        }

    var countryName: String?
        get() = sharedPreferences?.getString(COUNTRY_NAME_KEY, null)
        set(value) {
            value?.let { sharedPreferences?.edit()?.putString(COUNTRY_NAME_KEY, it) }?.apply()
        }

    fun getNetworkDate(): String {
        val calendar = Calendar.getInstance()
        val timeZone = TimeZone.getTimeZone("UTC")
        val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
        dateFormat.timeZone = timeZone
        return dateFormat.format(calendar.time)
    }

    fun deleteAllSharedPreferences(){
        sharedPreferences?.edit()?.clear()?.apply()
    }
}
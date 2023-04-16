package com.example.weatherwardrobe.core.util

import androidx.fragment.app.Fragment
import com.example.weatherwardrobe.R

fun Fragment.changeFragment(toFragment: Fragment){
    activity?.supportFragmentManager?.beginTransaction()?.apply {
        replace(R.id.fragment_container_view,toFragment)
        commit()
    }
}
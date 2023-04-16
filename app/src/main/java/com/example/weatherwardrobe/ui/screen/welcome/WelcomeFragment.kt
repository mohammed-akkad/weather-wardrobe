package com.example.weatherwardrobe.ui.screen.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import com.example.weatherwardrobe.R
import com.example.weatherwardrobe.core.data.local.PrefsUtil
import com.example.weatherwardrobe.databinding.FragmentWelcomeBinding
import com.example.weatherwardrobe.ui.base.BaseFragment
import com.example.weatherwardrobe.ui.screen.home.HomeFragment
import com.example.weatherwardrobe.core.util.changeFragment
import com.example.weatherwardrobe.core.util.isCountryNameValid
import com.google.android.material.snackbar.Snackbar

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWelcomeBinding =
        FragmentWelcomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBack()
    }

    private fun addCallBack() {
        binding.buttonSubmit.setOnClickListener {
            validateCountry()
        }
    }

    private fun validateCountry() {
        val country = binding.textInputEditTextCountry.text.toString().trim()
        if (country.isEmpty()) {
            Snackbar.make(binding.root, getString(R.string.please_enter_country_name), Snackbar.LENGTH_SHORT).show()
        } else if(!country.isCountryNameValid()) {
            Snackbar.make(binding.root, getString(R.string.country_name_invalid), Snackbar.LENGTH_SHORT).show()
        } else{
            PrefsUtil.countryName = country
            changeFragment(HomeFragment())
        }
    }
}
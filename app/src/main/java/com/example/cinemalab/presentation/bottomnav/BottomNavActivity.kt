package com.example.cinemalab.presentation.bottomnav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cinemalab.R
import com.example.cinemalab.databinding.ActivityBottomNavBinding
import com.example.cinemalab.presentation.bottomnav.profilescreen.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavActivity : AppCompatActivity(), ProfileFragment.ProfileListener {

    private lateinit var binding: ActivityBottomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)

        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.bottom_nav_content)
        navView.setupWithNavController(navController)

    }

    override fun onLogout() {
        finish()
    }
}
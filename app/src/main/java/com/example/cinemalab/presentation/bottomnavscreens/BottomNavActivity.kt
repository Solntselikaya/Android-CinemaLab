package com.example.cinemalab.presentation.bottomnavscreens

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cinemalab.R
import com.example.cinemalab.databinding.ActivityBottomNavBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding
    /*val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.bottom_nav_content) as NavHostFragment).navController
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)

        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.bottom_nav_content)
        navView.setupWithNavController(navController)

    }
}
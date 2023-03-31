package com.example.cinemalab.presentation.authscreens

import com.example.cinemalab.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)
        setContentView(R.layout.activity_main)
    }
}
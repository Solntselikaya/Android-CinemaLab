package com.example.cinemalab.presentation.authscreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemalab.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)
        setContentView(R.layout.activity_main)
    }
}
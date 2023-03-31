package com.example.cinemalab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cinemalab.databinding.FragmentBottomNavBarBinding

class BottomNavBarFragment : Fragment() {

    private lateinit var binding: FragmentBottomNavBarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_bottom_nav_bar, container, false)
        binding = FragmentBottomNavBarBinding.bind(mainView)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavBar.setupWithNavController(findNavController())
    }

}
package com.example.cinemalab.presentation.bottomnavscreens.collectionsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentCompilationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionsFragment : Fragment() {

    private lateinit var binding: FragmentCompilationBinding
    private val viewModel: CollectionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_collections, container, false)
        binding = FragmentCompilationBinding.bind(view)

        return binding.root
    }

}
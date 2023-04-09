package com.example.cinemalab.presentation.collectionactions.iconselectionscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentIconSelectBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
class IconSelectFragment : Fragment() {

    private lateinit var binding: FragmentIconSelectBinding
    private val viewModel: IconSelectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_icon_select, container, false)
        binding = FragmentIconSelectBinding.bind(view)

        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val stateObserver = Observer<IconSelectViewModel.IconSelectState> { newState ->
            when(newState) {
                IconSelectViewModel.IconSelectState.Initial -> {
                    val staggeredGridLayoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
                    val adapter = IconSelectAdapter(getIconsList())

                    binding.rvIcons.layoutManager = staggeredGridLayoutManager
                    binding.rvIcons.adapter = adapter
                }
                IconSelectViewModel.IconSelectState.Loading -> {

                }
                is IconSelectViewModel.IconSelectState.Failure -> {

                }
                IconSelectViewModel.IconSelectState.Success -> {

                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun getIconsList(): List<Int> {
        val iconsList: MutableList<Int> = mutableListOf()
        iconsList.add(R.drawable.collection_icon_01)
        iconsList.add(R.drawable.collection_icon_02)
        iconsList.add(R.drawable.collection_icon_03)
        iconsList.add(R.drawable.collection_icon_04)
        iconsList.add(R.drawable.collection_icon_05)
        iconsList.add(R.drawable.collection_icon_06)
        iconsList.add(R.drawable.collection_icon_07)
        iconsList.add(R.drawable.collection_icon_08)
        iconsList.add(R.drawable.collection_icon_09)
        iconsList.add(R.drawable.collection_icon_10)
        iconsList.add(R.drawable.collection_icon_11)
        iconsList.add(R.drawable.collection_icon_12)
        iconsList.add(R.drawable.collection_icon_13)
        iconsList.add(R.drawable.collection_icon_14)
        iconsList.add(R.drawable.collection_icon_15)
        iconsList.add(R.drawable.collection_icon_16)
        iconsList.add(R.drawable.collection_icon_17)
        iconsList.add(R.drawable.collection_icon_18)
        iconsList.add(R.drawable.collection_icon_19)
        iconsList.add(R.drawable.collection_icon_20)
        iconsList.add(R.drawable.collection_icon_21)
        iconsList.add(R.drawable.collection_icon_22)
        iconsList.add(R.drawable.collection_icon_23)
        iconsList.add(R.drawable.collection_icon_24)
        iconsList.add(R.drawable.collection_icon_25)
        iconsList.add(R.drawable.collection_icon_26)
        iconsList.add(R.drawable.collection_icon_27)
        iconsList.add(R.drawable.collection_icon_28)
        iconsList.add(R.drawable.collection_icon_29)
        iconsList.add(R.drawable.collection_icon_30)
        iconsList.add(R.drawable.collection_icon_31)
        iconsList.add(R.drawable.collection_icon_32)
        iconsList.add(R.drawable.collection_icon_33)
        iconsList.add(R.drawable.collection_icon_34)
        iconsList.add(R.drawable.collection_icon_35)
        iconsList.add(R.drawable.collection_icon_36)

        return iconsList
    }

}
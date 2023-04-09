package com.example.cinemalab.presentation.collectionactions.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemalab.R
import com.example.cinemalab.presentation.collectionactions.create.createcollectionscreen.CreateCollectionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionCreateActivity :
    AppCompatActivity(),
    CreateCollectionFragment.CollectionCreateListener
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)
        setContentView(R.layout.activity_collection_create)
    }

    override fun onCollectionCreated() {
        finish()
    }

    override fun onCreateBackPressed() {
        finish()
    }
}
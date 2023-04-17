package com.example.cinemalab.presentation.collectionactions.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.presentation.collectionactions.create.createcollectionscreen.CreateCollectionFragment
import com.example.cinemalab.presentation.collectionactions.iconselectionscreen.IconSelectFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionCreateActivity :
    AppCompatActivity(),
    CreateCollectionFragment.CollectionCreateListener,
    IconSelectFragment.IconSelectListener
{

    private var collection = CollectionModel("", "", R.drawable.collection_icon_01)

    fun getCollection(): CollectionModel {
        return collection
    }

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

    override fun onIconSelected(newIcon: Int) {
        collection = CollectionModel(
            id = collection.id,
            name = collection.name,
            icon = newIcon
        )
    }
}
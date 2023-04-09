package com.example.cinemalab.presentation.collectionactions.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.presentation.collectionactions.edit.collectiondetailsscreen.CollectionDetailsFragment
import com.example.cinemalab.presentation.collectionactions.edit.editcollectionscreen.EditCollectionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionEditActivity :
    AppCompatActivity(),
    CollectionDetailsFragment.CollectionDetailsListener,
    EditCollectionFragment.CollectionEditListener
{

    private val args: CollectionEditActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)
        setContentView(R.layout.activity_collection_edit)
    }

    override fun onDetailsBackPressed() {
        finish()
    }

    override fun onCollectionDelete() {
        finish()
    }

    fun getCollectionModel(): CollectionModel {
        return args.collectionModel
    }

}
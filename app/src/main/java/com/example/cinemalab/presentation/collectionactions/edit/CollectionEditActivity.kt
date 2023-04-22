package com.example.cinemalab.presentation.collectionactions.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.presentation.collectionactions.edit.collectiondetailsscreen.CollectionDetailsFragment
import com.example.cinemalab.presentation.collectionactions.edit.editcollectionscreen.EditCollectionFragment
import com.example.cinemalab.presentation.collectionactions.iconselectionscreen.IconSelectFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionEditActivity :
    AppCompatActivity(),
    CollectionDetailsFragment.CollectionDetailsListener,
    EditCollectionFragment.CollectionEditListener,
    IconSelectFragment.IconSelectListener {

    private val args: CollectionEditActivityArgs by navArgs()
    private var currCollection = CollectionModel("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        currCollection = CollectionModel(
            args.collectionInfo.id,
            args.collectionInfo.name,
            args.collectionInfo.icon
        )
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)
        setContentView(R.layout.activity_collection_edit)
    }

    override fun onDetailsBackPressed() {
        finish()
    }

    override fun onCollectionNameEdit(newName: String) {
        currCollection = CollectionModel(
            id = args.collectionInfo.id,
            name = newName,
            icon = currCollection.icon
        )
    }

    override fun onCollectionDelete() {
        finish()
    }

    override fun onIconSelected(icon: Int) {
        currCollection = CollectionModel(
            id = args.collectionInfo.id,
            name = currCollection.name,
            icon = icon
        )
    }

    fun getCollectionModel(): CollectionModel {
        return args.collectionInfo
    }

    fun getCurrCollection(): CollectionModel {
        return currCollection
    }

}
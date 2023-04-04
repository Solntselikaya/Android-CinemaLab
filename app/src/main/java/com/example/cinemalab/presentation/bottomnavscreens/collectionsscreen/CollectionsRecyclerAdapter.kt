package com.example.cinemalab.presentation.bottomnavscreens.collectionsscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.domain.model.MovieModel

class CollectionsRecyclerAdapter(
    private val collections: List<CollectionModel>
) : RecyclerView.Adapter<CollectionsRecyclerAdapter.CollectionsViewHolder>() {

    class CollectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.ivIcon)
        val name: TextView = itemView.findViewById(R.id.tvCollectionName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection, parent, false)
        return CollectionsViewHolder(itemView)
    }

    override fun getItemCount() = collections.size

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.icon.setImageResource(R.drawable.collection_icon_default)
        holder.name.text = collections[position].name
    }

}
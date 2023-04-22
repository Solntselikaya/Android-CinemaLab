package com.example.cinemalab.presentation.bottomnav.collectionsscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.CollectionModel

class CollectionsRecyclerAdapter(
    private val collections: List<CollectionModel>,
    private val onItemClick: (CollectionModel) -> Unit
) : RecyclerView.Adapter<CollectionsRecyclerAdapter.CollectionsViewHolder>() {

    class CollectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.ivIcon)
        val name: TextView = itemView.findViewById(R.id.tvCollectionName)

        val item: View = itemView.findViewById(R.id.item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection, parent, false)
        return CollectionsViewHolder(itemView)
    }

    override fun getItemCount() = collections.size

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.icon.setImageResource(collections[position].icon ?: R.drawable.collection_icon_01)
        holder.name.text = collections[position].name

        holder.item.setOnClickListener {
            onItemClick(collections[position])
        }
    }

}
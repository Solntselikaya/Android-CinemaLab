package com.example.cinemalab.presentation.collectionactions.iconselectionscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemalab.R

class IconSelectAdapter(private val icons: List<Int>) : RecyclerView.Adapter<IconSelectAdapter.SelectIconViewHolder>() {

    inner class SelectIconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectIconViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_select_icon, parent, false)
        return SelectIconViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectIconViewHolder, position: Int) {
        holder.icon.setImageResource(icons[position])
    }

    override fun getItemCount(): Int {
        return icons.size
    }

}
package com.example.cinemalab.presentation.movie.moviedetailsscreen.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.TagModel

class MovieTagsRecyclerAdapter(
    private val tags: List<TagModel>
) : RecyclerView.Adapter<MovieTagsRecyclerAdapter.MovieTagsViewHolder>() {

    class MovieTagsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvTagName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTagsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag, parent, false)
        return MovieTagsViewHolder(itemView)
    }

    override fun getItemCount() = tags.size

    override fun onBindViewHolder(holder: MovieTagsViewHolder, position: Int) {
        holder.name.text = tags[position].tagName
    }

}
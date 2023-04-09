package com.example.cinemalab.presentation.collectionactions.edit.collectiondetailsscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.MovieModel

class CollectionMoviesRecyclerAdapter(
    private val movies: List<MovieModel>
) : RecyclerView.Adapter<CollectionMoviesRecyclerAdapter.CollectionMoviesViewHolder>() {

    class CollectionMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cover: ImageView = itemView.findViewById(R.id.ivCover)
        val name: TextView = itemView.findViewById(R.id.tvMovieName)
        val description: TextView = itemView.findViewById(R.id.tvMovieDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionMoviesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_in_collection, parent, false)
        return CollectionMoviesViewHolder(itemView)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: CollectionMoviesViewHolder, position: Int) {
        Glide.with(holder.cover).load(movies[position].poster).into(holder.cover)
        holder.name.text = movies[position].name
        holder.description.text = movies[position].description
    }

}
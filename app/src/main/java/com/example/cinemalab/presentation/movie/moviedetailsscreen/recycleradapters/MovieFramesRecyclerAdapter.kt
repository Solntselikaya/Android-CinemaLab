package com.example.cinemalab.presentation.movie.moviedetailsscreen.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemalab.R

class MovieFramesRecyclerAdapter(
    private val frames: List<String>
) : RecyclerView.Adapter<MovieFramesRecyclerAdapter.MovieFramesViewHolder>() {

    class MovieFramesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val frame: ImageView = itemView.findViewById(R.id.ivFrame)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFramesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_frame, parent, false)
        return MovieFramesViewHolder(itemView)
    }

    override fun getItemCount() = frames.size

    override fun onBindViewHolder(holder: MovieFramesViewHolder, position: Int) {
        Glide.with(holder.frame).load(frames[position]).into(holder.frame)
    }

}
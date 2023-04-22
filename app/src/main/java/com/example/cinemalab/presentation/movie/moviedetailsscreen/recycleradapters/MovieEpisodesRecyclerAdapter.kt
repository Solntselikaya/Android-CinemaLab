package com.example.cinemalab.presentation.movie.moviedetailsscreen.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.EpisodeModel

class MovieEpisodesRecyclerAdapter(
    private val episodes: List<EpisodeModel>,
    private val navigate: (EpisodeModel) -> Unit
) : RecyclerView.Adapter<MovieEpisodesRecyclerAdapter.MovieEpisodesViewHolder>() {

    class MovieEpisodesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cover: ImageView = itemView.findViewById(R.id.ivEpisodeCover)
        val name: TextView = itemView.findViewById(R.id.tvEpisodeName)
        val description: TextView = itemView.findViewById(R.id.tvEpisodeDescription)
        val year: TextView = itemView.findViewById(R.id.tvYear)
        val item: View = itemView.findViewById(R.id.itemEpisode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieEpisodesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode_in_details, parent, false)
        return MovieEpisodesViewHolder(itemView)
    }

    override fun getItemCount() = episodes.size

    override fun onBindViewHolder(holder: MovieEpisodesViewHolder, position: Int) {
        Glide.with(holder.cover).load(episodes[position].preview).into(holder.cover)
        holder.name.text = episodes[position].name
        holder.description.text = episodes[position].description
        holder.year.text = episodes[position].year

        holder.item.setOnClickListener { navigate(episodes[position]) }
    }

}
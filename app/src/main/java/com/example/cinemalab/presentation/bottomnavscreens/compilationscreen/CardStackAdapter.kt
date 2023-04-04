package com.example.cinemalab.presentation.bottomnavscreens.compilationscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.MovieModel

class CardStackAdapter(
    private var movies: List<MovieModel> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.CardStackViewHolder>() {

    class CardStackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cover: ImageView = itemView.findViewById(R.id.ivMovie)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardStackViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_stack, parent, false)
        return CardStackViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: CardStackViewHolder,
        position: Int
    ) {
        Glide.with(holder.cover).load(movies[position].poster).into(holder.cover)
    }

    override fun getItemCount() = movies.size

    fun setMovies(newMovies: List<MovieModel>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }

    fun getMovies() = movies

}
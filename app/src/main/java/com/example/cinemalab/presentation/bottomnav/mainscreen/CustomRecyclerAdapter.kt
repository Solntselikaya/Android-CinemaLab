package com.example.cinemalab.presentation.bottomnav.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.MovieModel

class CustomRecyclerAdapter(
    private val itemLayout: Int,
    private val movies: List<MovieModel>,
    private val fragment: Fragment,
    private val onClick: (MovieModel) -> Unit
) : RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cover: ImageView = itemView.findViewById(R.id.ivCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Glide.with(fragment).load(movies[position].poster).into(holder.cover)
        holder.cover.setOnClickListener {
            onClick(movies[position])
        }
    }

}
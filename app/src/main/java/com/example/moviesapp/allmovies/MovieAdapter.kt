package com.example.moviesapp.allmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.MovieItemBinding
import com.example.moviesapp.retrofit.ApiMovie
import com.example.moviesapp.utils.IMAGE_BASE_URL
import com.example.moviesapp.utils.setResizableText
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val movieList: List<ApiMovie>,
    private val callback: (movieId: String) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MoviesItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesItemViewHolder =
        MoviesItemViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MoviesItemViewHolder, position: Int) {
        val item = movieList[position]
        with(holder.binding) {
            root.setOnClickListener {
                callback(item.id.toString())
            }
            Picasso.get().load(IMAGE_BASE_URL+item.path).into(movieImage)
            movieTitle.text= item.movieTitle
            voteAverage.text = item.voteAverage.toString()
            movieLanguage.text = movieLanguage.context.resources.getString(R.string.language_label, item.originalLanguage)
            releaseDate.text = releaseDate.context.resources.getString(R.string.release_date_label, item.releaseDate)
            overview.text = item.overview
            overview.setResizableText(item.overview?:"No text", 2, true)
        }
    }

    inner class MoviesItemViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)
}

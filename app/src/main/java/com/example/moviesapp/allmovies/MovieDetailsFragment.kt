package com.example.moviesapp.allmovies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import com.example.moviesapp.retrofit.MovieDetails
import com.example.moviesapp.utils.IMAGE_BASE_URL
import com.example.moviesapp.utils.makeLinks
import com.squareup.picasso.Picasso

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movie: MovieDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movie = MovieDetailsFragmentArgs.fromBundle(it).movieDetails
        }
        populateWidgets()
    }

    private fun populateWidgets() {
        binding.apply {
            movieTitle.text = movie.movieTitle
            overview.text = movie.overview
            Picasso.get().load(IMAGE_BASE_URL +movie.path).into(moviePoster)
            Picasso.get().load(IMAGE_BASE_URL +movie.posterPath).into(movieImage)
            homepage.text = requireContext().resources.getString(R.string.more_on, movie.homepage)
            createHomepageLink(movie.homepage)
            genres.text = movie.genreIds.map { it.name }.joinToString(", ")
            releaseDate.text = requireContext().resources.getString(R.string.release_date_label, movie.releaseDate)
            movieRevenue.text = requireContext().resources.getString(R.string.revenue_label, (movie.revenue/100000f).toString())
            movieBudget.text = requireContext().resources.getString(R.string.budget_label, (movie.budget/100000f).toString())
        }
    }

    private fun createHomepageLink(homepage: String) {
        binding.homepage.makeLinks(
            Pair(
                homepage,
                View.OnClickListener {
                    val uri: Uri = Uri.parse(homepage)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            )
        )
    }
}
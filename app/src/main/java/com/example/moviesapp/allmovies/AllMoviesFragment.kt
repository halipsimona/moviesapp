package com.example.moviesapp.allmovies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentAllMoviesBinding
import com.example.moviesapp.utils.FIREBASE_REFERENCE_URL
import com.example.moviesapp.utils.Success
import com.example.moviesapp.watchlist.WatchlistRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AllMoviesFragment : Fragment() {
    private lateinit var binding: FragmentAllMoviesBinding
    private lateinit var database: DatabaseReference
    private val viewModel: AllMoviesViewModel by viewModels {
        AllMoviesViewModelFactory(AllMoviesRepository(FirebaseDatabase.getInstance(FIREBASE_REFERENCE_URL)), WatchlistRepository(FirebaseDatabase.getInstance(FIREBASE_REFERENCE_URL)))
    }
    private val observer = Observer<com.example.moviesapp.utils.Result<MovieViewState>> {
        if (it is Success) handleResponse(it.data)
        else Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_LONG).show()
    }
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var accountId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMoviesBinding.inflate(layoutInflater, container, false)
        database = Firebase.database.reference
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        accountId = sharedPref.getString(getString(R.string.user_id), "") ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner, observer)
        viewModel.getPopularMovieList(accountId)
    }

    private fun handleResponse(viewState: MovieViewState) {
        if (viewState.addedToWatchList) {
            viewModel.getPopularMovieList(accountId)
        } else if (viewState.movieList != null) {
            movieAdapter = MovieAdapter(viewState.movieList.movieList, false, { id -> fetchDetails(id) }, { id -> addToWatchList(id) })
            binding.moviesRecyclerView.apply {
                itemAnimator = null
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
        } else if (viewState.movieDetails != null) {
            findNavController().navigate(AllMoviesFragmentDirections.actionToMovieDetails(viewState.movieDetails))
        }
    }

    private fun fetchDetails(id: String) {
        viewModel.getMovieDetails(id)
    }

    private fun addToWatchList(movieId: String) {
        viewModel.addToWatchList(accountId, movieId)
    }
}
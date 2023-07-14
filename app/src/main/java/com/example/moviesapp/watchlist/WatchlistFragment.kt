package com.example.moviesapp.watchlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.allmovies.MovieAdapter
import com.example.moviesapp.databinding.FragmentWatchlistBinding
import com.example.moviesapp.utils.FIREBASE_REFERENCE_URL
import com.example.moviesapp.utils.Result
import com.example.moviesapp.utils.Success
import com.google.firebase.database.FirebaseDatabase

class WatchlistFragment : Fragment() {

    private lateinit var binding: FragmentWatchlistBinding
    private var accountId: String = ""
    private val viewModel: WatchlistViewModel by viewModels {
        WatchlistViewModelFactory(WatchlistRepository(FirebaseDatabase.getInstance(FIREBASE_REFERENCE_URL)))
    }
    private val observer = Observer<Result<WatchlistViewState>> {
        if (it is Success) handleResponse(it.data)
        else Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_LONG).show()
    }
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchlistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        accountId = sharedPref.getString(getString(R.string.user_id), "") ?: ""
        viewModel.liveData.observe(viewLifecycleOwner, observer)
        viewModel.getWatchlist(accountId)
    }

    private fun handleResponse(viewState: WatchlistViewState) {
        if (viewState.movieList != null) {
            movieAdapter = MovieAdapter(viewState.movieList.movieList, true, { id -> fetchDetails(id) }, { id -> addToWatchList(id) })
            binding.moviesRecyclerView.apply {
                itemAnimator = null
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
        }
    }

    private fun fetchDetails(movieId: String) {

    }

    private fun addToWatchList(movieId: String) {

    }
}
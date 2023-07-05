package com.example.moviesapp.login

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
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSignupBinding
import com.example.moviesapp.utils.Failure
import com.example.moviesapp.utils.Result
import com.example.moviesapp.utils.Success

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: SignupViewModel by viewModels {
        SignupViewModelFactory(
            SignupRepository()
        )
    }
    private val observer = Observer<Result<User>> { handleResponse(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.signupLD.observe(viewLifecycleOwner, observer)
        binding.signupButton.setOnClickListener {
            signUp()
        }
    }

    private fun handleResponse(result: Result<User>) {
        if (result is Success) {
            saveUserData(result.data)
            navigateToMainPage()
        } else if (result is Failure){
            Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun signUp() {
        viewModel.signUp(
            binding.emailEditText.text.toString().trim(),
            binding.passwordEditText.text.toString().trim()
        )
    }

    private fun saveUserData(user: User) {
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putString(getString(R.string.user_id), user.userId)
            putString(getString(R.string.user_email), user.email)
            apply()
        }
    }

    private fun navigateToMainPage() {
        findNavController().navigate(R.id.action_toAllMoviesFragment2)
    }
}
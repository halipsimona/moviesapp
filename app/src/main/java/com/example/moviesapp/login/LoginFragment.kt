package com.example.moviesapp.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentLoginBinding
import com.example.moviesapp.utils.Failure
import com.example.moviesapp.utils.Result
import com.example.moviesapp.utils.Success
import com.example.moviesapp.utils.makeLinks
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels {
        LogInViewModelFactory(
            LoginRepository()
        )
    }
    private val observer = Observer<Result<User>> { handleResponse(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(requireContext())
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.signinLD.observe(viewLifecycleOwner, observer)
        createSignupLink()
        loginToMoviesApp()
    }

    private fun createSignupLink() {
        binding.signUpMessage.makeLinks(
            Pair(
                getString(R.string.sign_up_here),
                View.OnClickListener {
                    findNavController().navigate(R.id.action_toSignupFragment)
                }
            )
        )
    }

    private fun loginToMoviesApp() {
        binding.loginButton.setOnClickListener {
            viewModel.signIn(
                binding.emailEditText.text.toString().trim(),
                binding.passwordEditText.text.toString().trim()
            )
        }
    }

    private fun handleResponse(result: Result<User>) {
        if (result is Success) {
            saveUserData(result.data)
            navigateToMainPage()
        } else if (result is Failure) {
            Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG).show()
        }
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
        findNavController().navigate(R.id.action_toAllMoviesFragment)
    }
}
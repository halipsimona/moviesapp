package com.example.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController: NavController
        get() = findNavController(R.id.navigation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.menu.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(fragmentNavigationListener)
        navigateToLogin()
    }

    private val fragmentNavigationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.loginFragment, R.id.signupFragment -> {
                binding.menu.visibility = View.GONE
            }
            else -> binding.menu.visibility = View.VISIBLE
        }
    }

    private fun navigateToLogin() {
        navController.navigate(R.id.action_global_loginFragment)
    }
}
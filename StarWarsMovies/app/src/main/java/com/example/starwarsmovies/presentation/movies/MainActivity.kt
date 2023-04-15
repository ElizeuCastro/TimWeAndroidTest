package com.example.starwarsmovies.presentation.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.starwarsmovies.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by viewModel {
        parametersOf(SavedStateHandle())
    }
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel.state.observe(this) { state ->
            if (state.isLoading) {
                binding.progressBar.isVisible = true
                binding.tvError.isVisible = false
                binding.rvMovies.isVisible = false
            }
            if (state.error.isNotBlank()) {
                binding.progressBar.isVisible = false
                binding.tvError.isVisible = false
                binding.rvMovies.isVisible = true

                binding.tvError.text = state.error
            }
            if (state.movies.isNotEmpty()) {
                binding.progressBar.isVisible = false
                binding.tvError.isVisible = false
                binding.rvMovies.isVisible = true

                val adapter = MoviesAdapter()
                binding.rvMovies.adapter = adapter
                adapter.submitList(state.movies)
            }
        }

    }
}
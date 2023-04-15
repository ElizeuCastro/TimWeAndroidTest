package com.example.starwarsmovies.presentation.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.SavedStateHandle
import com.example.starwarsmovies.databinding.ActivityMainBinding
import com.example.starwarsmovies.domain.model.Movie
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
                showProgressBar()
            }
            if (state.error.isNotBlank()) {
                showError(state.error)
            }
            if (state.movies.isNotEmpty()) {
                showMovies(state.movies)
            }
        }

    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
        binding.tvError.isVisible = false
        binding.rvMovies.isVisible = false
    }

    private fun showError(error: String) {
        binding.rvMovies.isVisible = false
        binding.progressBar.isVisible = false
        binding.tvError.isVisible = true

        binding.tvError.text = error
    }

    private fun showMovies(movies: List<Movie>) {
        binding.progressBar.isVisible = false
        binding.tvError.isVisible = false
        binding.rvMovies.isVisible = true

        val adapter = MoviesAdapter()
        binding.rvMovies.adapter = adapter
        adapter.submitList(movies)
    }
}
package com.example.starwarsmovies.presentation.movies

import androidx.lifecycle.*
import com.example.starwarsmovies.common.Constants
import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.domain.model.Movie
import com.example.starwarsmovies.domain.useCase.GetMovieUseCase
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getMovieUseCase: GetMovieUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData(MoviesListState())
    val state: LiveData<MoviesListState> = _state

    init {
        savedStateHandle.get<List<Movie>>(Constants.MOVIES_LIST_KEY)?.let { savedMovies ->
            _state.value = MoviesListState(movies = savedMovies)
        } ?: run {
            getMovies()
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            _state.value = MoviesListState(isLoading = true)
            when (val result = getMovieUseCase()) {
                is Resource.Success -> {
                    val movies = result.data ?: emptyList()
                    _state.value = MoviesListState(movies = movies)
                    savedStateHandle[Constants.MOVIES_LIST_KEY] = movies
                }
                is Resource.Error -> {
                    _state.value = MoviesListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = MoviesListState(isLoading = true)
                }
            }
        }
    }
}
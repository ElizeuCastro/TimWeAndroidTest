package com.example.starwarsmovies.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.starwarsmovies.common.Constants
import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.domain.model.Movie
import com.example.starwarsmovies.domain.useCase.GetMovieUseCase
import com.example.starwarsmovies.presentation.movies.MoviesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieUseCase: GetMovieUseCase

    private lateinit var moviesViewModel: MoviesViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `show movies list with success`() = runTest {

        val mockedMovies = provideMockedMovies()
        coEvery { movieUseCase() } returns Resource.Success(mockedMovies)

        moviesViewModel = MoviesViewModel(movieUseCase, SavedStateHandle())

        advanceUntilIdle()

        coVerify(exactly = 1) { movieUseCase() }
        assertEquals(false, moviesViewModel.state.getOrAwaitValue().isLoading)
        assertEquals("", moviesViewModel.state.getOrAwaitValue().error)
        assertEquals(mockedMovies, moviesViewModel.state.getOrAwaitValue().movies)

    }

    @Test
    fun `show message error when occur any service error`() = runTest {
        val mockedError = "Any Error"
        coEvery { movieUseCase() } returns Resource.Error("Any Error")

        moviesViewModel = MoviesViewModel(movieUseCase, SavedStateHandle())

        advanceUntilIdle()

        coVerify(exactly = 1) { movieUseCase() }
        assertEquals(false, moviesViewModel.state.getOrAwaitValue().isLoading)
        assertEquals(mockedError, moviesViewModel.state.getOrAwaitValue().error)
        assertEquals(0, moviesViewModel.state.getOrAwaitValue().movies.size)
    }

    @Test
    fun `show movies list from saved state handle`() {
        val mockedMovies = provideMockedMovies()
        val savedMovies = mapOf(Constants.MOVIES_LIST_KEY to mockedMovies)
        val savedStateHandle = SavedStateHandle(savedMovies)

        moviesViewModel = MoviesViewModel(movieUseCase, savedStateHandle)

        coVerify(exactly = 0) { movieUseCase() }
        assertEquals(false, moviesViewModel.state.getOrAwaitValue().isLoading)
        assertEquals("", moviesViewModel.state.getOrAwaitValue().error)
        assertEquals(mockedMovies, moviesViewModel.state.getOrAwaitValue().movies)
    }

    private fun provideMockedMovies() = listOf(
        Movie(
            cover = "https://www.coverwhiz.com/uploads/movies/star-wars-episode-iv-a-new-hope.jpg",
            director = "A New Hope",
            episodeId = "George Lucas",
            releaseDate = "IV",
            title = "25-05-1977"
        )
    )

}
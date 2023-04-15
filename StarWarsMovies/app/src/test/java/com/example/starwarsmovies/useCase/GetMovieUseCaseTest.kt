package com.example.starwarsmovies.useCase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.domain.model.Movie
import com.example.starwarsmovies.domain.repository.MovieRepository
import com.example.starwarsmovies.domain.useCase.GetMovieUseCase
import com.example.starwarsmovies.presentation.getOrAwaitValue
import com.example.starwarsmovies.presentation.movies.MoviesViewModel
import com.example.starwarsmovies.respository.FakeMovieRepository
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
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GetMovieUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var getMovieUseCase: GetMovieUseCase

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
    fun `get movies with success`() = runTest {

        getMovieUseCase = GetMovieUseCase(FakeMovieRepository())

        val resourceResult = getMovieUseCase()

        assertTrue(resourceResult is Resource.Success)
        assertEquals(1, resourceResult.data!!.size)
        assertEquals("A New Hope", resourceResult.data!![0].title)

    }

    @Test
    fun `get movies with error`() = runTest {

        getMovieUseCase = GetMovieUseCase(FakeMovieRepository(shouldReturnSuccess = false))

        val resourceResult = getMovieUseCase()

        assertTrue(resourceResult is Resource.Error)
        assertEquals("An unexpected error occurred", resourceResult.data!![0].title)

    }
}


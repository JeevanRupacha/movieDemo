package com.demo.moviedemo.features.moveList

import app.cash.turbine.test
import com.demo.moviedemo.core.utils.LoadingState
import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.model.MovieID
import com.demo.moviedemo.data.model.MovieIDResponse
import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.utils.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var viewModel: MovieListViewModel
    private lateinit var tmdbRepository: TMDBRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        tmdbRepository = mockk()
        viewModel = MovieListViewModel(tmdbRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        // Verify the initial state of the ViewModel
        val mockMovies = listOf(
            MovieID(id = 1212, adult = false),
            MovieID(id = 2323, adult = false)
        )

        val movies = listOf(
            MovieDetail(id = 1212, title = "movie1"),
            MovieDetail(id = 2323, title = "movie2")
        )
        val mockResponse = ApiResult.Success(MovieIDResponse(results = mockMovies))
        coEvery { tmdbRepository.getMovieIDList(page = 1) } returns mockResponse
        coEvery { tmdbRepository.getMovieDetail(1212) } returns ApiResult.Success(movies.first())
        coEvery { tmdbRepository.getMovieDetail(2323) } returns ApiResult.Success(movies.last())

        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.movies.isEmpty())
            assertEquals(LoadingState.IDLE.status.name, initialState.loadingState.status.name)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadMoreMovies updates state with new movies`() = runTest {
        // Arrange
        val mockMovies = listOf(
            MovieID(id = 1212, adult = false),
            MovieID(id = 2323, adult = false)
        )

        val movies = listOf(
            MovieDetail(id = 1212, title = "movie1"),
            MovieDetail(id = 2323, title = "movie2")
        )
        val mockResponse = ApiResult.Success(MovieIDResponse(results = mockMovies))
        coEvery { tmdbRepository.getMovieIDList(page = any()) } returns mockResponse
        coEvery { tmdbRepository.getMovieDetail(1212) } returns ApiResult.Success(movies.first())
        coEvery { tmdbRepository.getMovieDetail(2323) } returns ApiResult.Success(movies.last())

        // Act
        viewModel.loadMoreMovies(scope = testScope)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        viewModel.state.test {
            val updatedState = awaitItem()
            assertEquals(movies, updatedState.movies)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getMovieDetail handles error correctly`() = runTest {
        // Arrange
        coEvery {
            tmdbRepository.getMovieDetail(1L)
        } returns ApiResult.Success(MovieDetail(id = 1L, title = "movie1"))
        //} returns ApiResult.Error("Network error")

        // Act
        viewModel.getMovieDetail(1L, testScope)
        testDispatcher.scheduler.advanceUntilIdle()

//        // Assert
        val state = viewModel.state.value.loadingState
        assertEquals(LoadingState.ERROR.status.name, state.status.name)
    }
}
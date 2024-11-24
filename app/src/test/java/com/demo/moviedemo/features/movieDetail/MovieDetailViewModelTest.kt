package com.demo.moviedemo.features.movieDetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.demo.moviedemo.core.utils.LoadingState
import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.utils.ApiResult
import com.demo.moviedemo.features.moveList.MovieListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var tmdbRepository: TMDBRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        tmdbRepository = mockk()
        savedStateHandle = SavedStateHandle(mapOf("id" to 123L))
        viewModel = MovieDetailViewModel(savedStateHandle, tmdbRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovieDetail updates state with movie details on success`() = runTest(testDispatcher) {
        // Arrange
        val movieDetail = MovieDetail(id = 123L, title = "Sample Movie")
        coEvery { tmdbRepository.getMovieDetail(any()) } returns ApiResult.Success(movieDetail)

        viewModel.getMovieDetail(123L, testScope)

        // Act
//        val movie = viewModel.state.first().movie
//        testDispatcher.scheduler.advanceUntilIdle()
//        assertEquals(movieDetail, movie)

        // Act
        viewModel.state.test {
            // Initial state
            val initialState = awaitItem()
            assertEquals(MovieDetailState(), initialState)

            // Loading state
            val loadingState = awaitItem()
            assertEquals(LoadingState.LOADING, loadingState.loadingState)

            // Loaded state
            val loadedState = awaitItem()
            assertEquals(LoadingState.LOADED, loadedState.loadingState)
            assertEquals(movieDetail, loadedState.movie) // Check the movie details
        }

//        viewModel.state.test {
//            // Initial state
//            assertEquals(MovieDetailState(), awaitItem())
//
//            // Loading state
//            assertEquals(MovieDetailState(loadingState = LoadingState.LOADING), awaitItem())
//
//            // Loaded state
//            assertEquals(
//                MovieDetailState(
//                    loadingState = LoadingState.LOADED,
//                    movie = movieDetail
//                ),
//                awaitItem()
//            )
//        }
    }

    @Test
    fun `getMovieDetail updates state with error on failure`() = runTest(testDispatcher) {
        // Arrange
        coEvery { tmdbRepository.getMovieDetail(123L) } returns ApiResult.Error("Network Error")

        // Act
        viewModel.state.test {
            // Initial state
            assertEquals(MovieDetailState(), awaitItem())

            // Loading state
            assertEquals(MovieDetailState(loadingState = LoadingState.LOADING), awaitItem())

            // Error state
            assertEquals(
                MovieDetailState(loadingState = LoadingState.ERROR),
                awaitItem()
            )
        }
    }

    @Test
    fun `getMovieDetail does nothing when id is null`() = runTest(testDispatcher) {
        // Arrange
        savedStateHandle = SavedStateHandle() // No id provided
        viewModel = MovieDetailViewModel(savedStateHandle, tmdbRepository)

        // Act
        viewModel.state.test {
            // Initial state
            assertEquals(MovieDetailState(), awaitItem())
        }
    }
}
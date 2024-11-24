package com.demo.moviedemo.features.movieDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.demo.moviedemo.core.utils.LoadingState
import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.utils.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var tmdbRepository: TMDBRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        tmdbRepository = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle(mapOf("id" to 123L))
        viewModel = MovieDetailViewModel(savedStateHandle, tmdbRepository)
        viewModel.setCoroutineScope(testScope)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovieDetail updates state to LOADED on success`() = runTest {
        // Mock the repository to return a successful result
        val movieDetail = MovieDetail(id = 123L, title = "Fake Movie")
        coEvery { tmdbRepository.getMovieDetail(123L) } returns ApiResult.Success(movieDetail)

        // Observe state changes using Turbine
        viewModel.getMovieDetail(123L)
        testDispatcher.scheduler.advanceUntilIdle()
        testScheduler.advanceUntilIdle()

        viewModel.state.test {
            // Expect loaded state with movie detail
            val loadedState = awaitItem()
            assert(loadedState.loadingState == LoadingState.LOADED)
            assert(loadedState.movie == movieDetail)
            assert(loadedState.movie?.title == "Fake Movie")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getMovieDetail updates state to ERROR on failure`() = runTest {
        // Mock the repository to return an error
        coEvery { tmdbRepository.getMovieDetail(1L) } returns ApiResult.Error("Error")

        viewModel.getMovieDetail(1L)
        testDispatcher.scheduler.advanceUntilIdle()

        // Observe state changes using Turbine
        viewModel.state.test {
            // Expect error state
            val errorState = awaitItem()
            assertEquals(LoadingState.ERROR.status.name, errorState.loadingState.status.name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getMovieDetail does not update state when id is null`() = runTest {
        // Create ViewModel without "id" in SavedStateHandle
        val savedStateHandle = SavedStateHandle()
        val viewModelWithoutId = MovieDetailViewModel(savedStateHandle, tmdbRepository)

        // Observe state changes using Turbine
        viewModelWithoutId.state.test {
            // Expect initial state (idle state)
            val initialState = awaitItem()
            assert(initialState.loadingState == LoadingState.IDLE)

            // Ensure no further state changes occur
            expectNoEvents()
        }
    }
}
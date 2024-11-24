package com.demo.moviedemo.e2e

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.demo.moviedemo.BuildScreen
import com.demo.moviedemo.MainActivity
import com.demo.moviedemo.core.theme.MovieDemoTheme
import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.model.MovieID
import com.demo.moviedemo.data.model.MovieIDResponse
import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.utils.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppEndToEndTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController
    private lateinit var tmdbRepository: TMDBRepository

    @Before
    fun setUp() {
        tmdbRepository = mockk()
        composeRule.activity.setContent {
            MovieDemoTheme {
                navController = rememberNavController()
                BuildScreen(
                    navController = navController,
                )
            }
        }
    }

    @Test
    fun app_travel_full_end_to_end_test(){
        val context = ApplicationProvider.getApplicationContext<Context>()

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

        composeRule.onNodeWithText("Movie List").assertIsDisplayed()
    }
}
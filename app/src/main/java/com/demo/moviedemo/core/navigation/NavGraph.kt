package com.demo.moviedemo.core.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.demo.moviedemo.core.navigation.NavigationUtils.enterTransition
import com.demo.moviedemo.core.navigation.NavigationUtils.exitTransition
import com.demo.moviedemo.core.navigation.NavigationUtils.popEnterTransition
import com.demo.moviedemo.core.navigation.NavigationUtils.popExitTransition
import com.demo.moviedemo.features.moveList.MovieListScreen
import com.demo.moviedemo.features.moveList.MovieListViewModel
import com.demo.moviedemo.features.movieDetail.MovieDetailScreen
import com.demo.moviedemo.features.movieDetail.MovieDetailViewModel

fun NavGraphBuilder.movieList(
    navController: NavController
) {
    composable<Screens.MovieList>(
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        val vm = hiltViewModel<MovieListViewModel>()
        val state by vm.state.collectAsStateWithLifecycle()
        val isLoading by vm.isLoading.collectAsStateWithLifecycle()

        MovieListScreen(
            state = state,
            onClickMovie = navController::goToMovieDetail,
            loadMoreMovies = vm::loadMoreMovies,
            isLoading = isLoading
        )
    }
}

fun NavGraphBuilder.movieDetail(
    navController: NavController
) {
    composable<Screens.MovieDetail>(
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
    ) {
        val vm = hiltViewModel<MovieDetailViewModel>()
        val state by vm.state.collectAsStateWithLifecycle()

        MovieDetailScreen(
            state = state,
            onBack = navController::navigateUp
        )
    }
}
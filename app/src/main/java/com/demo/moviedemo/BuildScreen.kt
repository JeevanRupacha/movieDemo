package com.demo.moviedemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.demo.moviedemo.core.navigation.Screens
import com.demo.moviedemo.core.navigation.movieDetail
import com.demo.moviedemo.core.navigation.movieList
import timber.log.Timber

@Composable
fun BuildScreen(
    navController: NavHostController
) {
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        // Log for debug to know routes
        Timber.d("arguments = $arguments, destination = $destination")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box {
            NavHost(
                navController = navController,
                startDestination = Screens.MovieList,
                builder = {
                    movieList(navController)
                    movieDetail(navController)
                }
            )
            // Top Level Components goes here
        }
    }
}
package com.demo.moviedemo.core.navigation

import androidx.navigation.NavController

// navigate to movie list screen with single top launch
fun NavController.goToMovieList(){
    this.navigate(Screens.MovieList){
        launchSingleTop = true
    }
}

// navigate to movie detail screen with movie id
fun NavController.goToMovieDetail(id: Long){
    this.navigate(Screens.MovieDetail(id)){
        launchSingleTop = true
    }
}



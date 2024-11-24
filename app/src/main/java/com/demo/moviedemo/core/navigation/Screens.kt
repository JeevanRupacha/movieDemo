package com.demo.moviedemo.core.navigation

import com.demo.moviedemo.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens (
    val titleNameId: Int = -1
) {
    @Serializable
    data object MovieList: Screens(R.string.movie_list)

    @Serializable
    data class MovieDetail(
        val id: Long? = null,
    ): Screens(R.string.movie_detail)
}
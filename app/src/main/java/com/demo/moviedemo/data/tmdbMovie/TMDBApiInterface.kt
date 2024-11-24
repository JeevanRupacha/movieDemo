package com.demo.moviedemo.data.tmdbMovie

import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.model.MovieIDResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiInterface {
    @GET("/3/movie/changes")
    suspend fun getMovieIDList(
        @Query("page") page: Int = 1
    ): Response<MovieIDResponse>

    @GET("/3/movie/{id}?language=en-US")
    suspend fun getMovieDetail(
        @Path("id") id: Long
    ): Response<MovieDetail>
}
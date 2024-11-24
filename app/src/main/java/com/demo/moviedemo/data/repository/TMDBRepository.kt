package com.demo.moviedemo.data.repository

import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.model.MovieIDResponse
import com.demo.moviedemo.data.utils.ApiResult

interface TMDBRepository {
    suspend fun getMovieIDList(page: Int) : ApiResult<MovieIDResponse>
    suspend fun getMovieDetail(id: Long): ApiResult<MovieDetail>
}
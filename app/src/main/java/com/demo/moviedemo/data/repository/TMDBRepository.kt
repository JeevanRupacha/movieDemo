package com.demo.moviedemo.data.repository

import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.model.MovieIDResponse
import com.demo.moviedemo.data.utils.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

interface TMDBRepository {
    suspend fun getMovieIDList(page: Int) : ApiResult<MovieIDResponse>
    suspend fun getMovieDetail(id: Long): ApiResult<MovieDetail>
}
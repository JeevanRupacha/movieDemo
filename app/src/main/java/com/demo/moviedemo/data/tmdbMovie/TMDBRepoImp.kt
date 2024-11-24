package com.demo.moviedemo.data.tmdbMovie

import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.utils.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher


class TMDBRepoImp(
    private val apiService: TMDBApiInterface
) : DataRepository(), TMDBRepository {
    override suspend fun getMovieIDList(page: Int) = handleApi{ apiService.getMovieIDList(page) }
    override suspend fun getMovieDetail(id: Long) = handleApi{ apiService.getMovieDetail(id)}
}
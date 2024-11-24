package com.demo.moviedemo.data.tmdbMovie

import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.utils.DataRepository


class TMDBRepoImp(
    private val apiService: TMDBApiInterface
) : DataRepository(), TMDBRepository {
    override suspend fun getMovieIDList(page: Int) = handleApi{ apiService.getMovieIDList(page) }
    override suspend fun getMovieDetail(id: Long) = handleApi{ apiService.getMovieDetail(id)}
}
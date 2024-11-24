package com.demo.moviedemo.di

import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.tmdbMovie.TMDBApiInterface
import com.demo.moviedemo.data.tmdbMovie.TMDBRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideTMDBRepository(
        tmdbApi: TMDBApiInterface,
    ): TMDBRepository {
        return TMDBRepoImp(tmdbApi)
    }
}
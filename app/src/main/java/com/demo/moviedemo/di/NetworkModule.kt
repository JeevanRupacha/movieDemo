package com.demo.moviedemo.di

import com.demo.moviedemo.BuildConfig
import com.demo.moviedemo.core.utils.Constants
import com.demo.moviedemo.data.tmdbMovie.TMDBApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = Constants.IMDB_BASE_URL

    @Singleton
    @Provides
    fun providerRetrofitInstance(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Connect timeout
            .readTimeout(120, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(60, TimeUnit.SECONDS)   // Write timeout

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ${BuildConfig.TMDB_AUTH_KEY}")
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        return retrofit
    }

    @Singleton
    @Provides
    fun provideTMDBApiInterface(retrofit: Retrofit): TMDBApiInterface {
        return retrofit.create(TMDBApiInterface::class.java)
    }
}
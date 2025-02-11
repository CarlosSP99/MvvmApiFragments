package com.utad.mvvm_api_fragments.mainView.network

import com.utad.mvvm_api_fragments.mainView.model.network.ApiResponse
import com.utad.mvvm_api_fragments.mainView.model.network.MovieModel
import com.utad.mvvm_api_fragments.mainView.model.network.SingleMovieModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovieClient {
    @GET("movie/upcoming?language=en-US")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): ApiResponse

    @GET("movie/popular?language=en-US")
    suspend fun getPopularMovies(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): ApiResponse

    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRatedMovies(
        @Header("Authorization") token: String,
        @Query("page") page: Int
        ): ApiResponse

    @GET("movie/upcoming?language=en-US")
    suspend fun getUpcomingMovies(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): ApiResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Header("Authorization") token: String,
        @Path("movie_id") movieId: Int
    ): SingleMovieModel

    @GET("discover/movie")
    suspend fun getMoviesWithSimilarGenre(
        @Header("Authorization") token: String,
        @Query
            ("with_genres") genreId: Int
    ): ApiResponse

}
package com.utad.mvvm_api_fragments.mainView.network

import com.utad.mvvm_api_fragments.mainView.model.network.ApiResponse
import com.utad.mvvm_api_fragments.mainView.model.network.MovieModel
import com.utad.mvvm_api_fragments.mainView.model.network.SingleMovieModel
import javax.inject.Inject

class MovieService@Inject constructor(private val api: ApiMovieClient){


    suspend fun getNowPlayingMovies(page: Int, token: String): ApiResponse {
        return api.getNowPlayingMovies(
            page = page,
            token = token,
        )
    }

    suspend fun getPopularMovies(page: Int, token: String): ApiResponse {
        return api.getPopularMovies(
            page = page,
            token = token,
        )
    }

    suspend fun getTopRatedMovies(page: Int, token: String): ApiResponse {
        return api.getTopRatedMovies(
            page = page,
            token = token,
        )
    }

    suspend fun getUpcomingMovies(page: Int, token: String): ApiResponse {
        return api.getUpcomingMovies(
            page = page,
            token = token,
        )
    }

    suspend fun getMovieDetails(movieId: Int, token: String): SingleMovieModel {
        return api.getMovieDetails(
            movieId = movieId,
            token = token,
        )
    }
    suspend fun getMoviesWithSimilarGenre(genreId: Int, token: String): ApiResponse {
        return api.getMoviesWithSimilarGenre(
            genreId = genreId,
            token = token,
        )
    }

}

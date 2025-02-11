package com.utad.mvvm_api_fragments.mainView.domain

import com.utad.mvvm_api_fragments.mainView.util.Constants.API_TOKEN
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.model.network.SingleMovieModel
import com.utad.mvvm_api_fragments.mainView.model.network.toDomain
import com.utad.mvvm_api_fragments.mainView.network.MovieService
import javax.inject.Inject

class MovieRepository@Inject constructor(private val api: MovieService) {

    suspend fun getNowPlayingMovies( page: Int, token: String = API_TOKEN): List<Movie> {
       val data =  api.getNowPlayingMovies(
            page=page,
            token = token
        )
        return data.results.map {
            toDomain(it)
        }
    }

    suspend fun getPopularMovies( page: Int, token: String = API_TOKEN): List<Movie> {
        val data =  api.getPopularMovies(
            page=page,
            token = token
        )
        return data.results.map {
            toDomain(it)
        }    }

    suspend fun getTopRatedMovies( page: Int, token: String = API_TOKEN): List<Movie> {
        val data =  api.getTopRatedMovies(
            page=page,
            token = token
        )
        return data.results.map {
            toDomain(it)
        }
    }

    suspend fun getUpcomingMovies( page: Int, token: String = API_TOKEN): List<Movie> {
        val data =  api.getUpcomingMovies(
            page=page,
            token = token
        )
        return data.results.map {
            toDomain(it)
        }
    }

    suspend fun getMovieDetails(movieId: Int, token: String = API_TOKEN): SingleMovie {
        val data = api.getMovieDetails(
            movieId = movieId,
            token = token
        )

        return data.toDomain()
    }
    suspend fun getMoviesWithSimilarGenre(genreId: Int, token: String = API_TOKEN): List<Movie> {
        val data =  api.getMoviesWithSimilarGenre(
            genreId = genreId,
            token = token
        )
        return data.results.map {
            toDomain(it)
        }
    }
    }




package com.utad.mvvm_api_fragments.mainView.model.repository

import android.util.Log
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.model.room.SingleMovieRoom
import com.utad.mvvm_api_fragments.mainView.model.room.User
import com.utad.mvvm_api_fragments.mainView.model.room.toDomain
import com.utad.mvvm_api_fragments.mainView.room.AppDatabase
import javax.inject.Inject

class repositoryRoom@Inject constructor(
    private val app: AppDatabase,
    ) {

    suspend fun registerUser(user: User){
        return app.userDao().insert(user)
    }
    suspend fun checkEmail(email:String): User {
        return app.userDao().checkEmail(email)
    }

    suspend fun login(user: String, password: String): User {
        return app.userDao().login(user, password)
    }

    suspend fun insertMovie(movie: SingleMovieRoom){
        return app.movieDao().insert(movie)
    }

    suspend fun removeMovie(movie: SingleMovieRoom) {
        return app.movieDao().delete(movie)
    }

    suspend fun checkMovie(id: Int): SingleMovieRoom {
        return app.movieDao().checkMovie(id)
    }

   suspend fun getMoviesBookMarked(): List<SingleMovie> {
        val data = app.movieDao().getMoviesBookMarked()
        val domain = data.map {
            it.toDomain()
        }
       for (i in domain.indices){
           Log.i("ESTA SALIENDO",domain[i].title)
       }
        return domain
       }

    suspend fun getMovieById(movieId: Int): SingleMovieRoom {
       return app.movieDao().getMovieById(movieId)
    }
}
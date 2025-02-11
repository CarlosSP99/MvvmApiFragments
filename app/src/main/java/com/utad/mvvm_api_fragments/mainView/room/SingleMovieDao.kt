package com.utad.mvvm_api_fragments.mainView.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.model.room.SingleMovieRoom

@Dao
interface SingleMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: SingleMovieRoom)

    @Delete
    suspend fun delete(movie: SingleMovieRoom)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun checkMovie(id: Int): SingleMovieRoom

    @Query("SELECT * FROM movies")
    suspend fun getMoviesBookMarked(): List<SingleMovieRoom>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): SingleMovieRoom

}
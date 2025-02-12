package com.utad.mvvm_api_fragments.mainView.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.utad.mvvm_api_fragments.mainView.model.room.SingleMovieRoom
import com.utad.mvvm_api_fragments.mainView.model.room.User

@Database(entities = [User::class, SingleMovieRoom::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun movieDao(): SingleMovieDao
}
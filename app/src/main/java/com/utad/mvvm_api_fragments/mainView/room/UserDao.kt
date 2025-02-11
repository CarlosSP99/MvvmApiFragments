package com.utad.mvvm_api_fragments.mainView.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utad.mvvm_api_fragments.mainView.model.room.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun checkEmail(email:String): User
    @Query("SELECT * FROM users WHERE first_name = :user AND pass = :password")
    suspend fun login(user:String, password:String): User

}
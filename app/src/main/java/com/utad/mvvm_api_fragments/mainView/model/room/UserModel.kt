package com.utad.mvvm_api_fragments.mainView.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int=0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "pass") val password: String,
    @ColumnInfo(name = "email") val email: String
)
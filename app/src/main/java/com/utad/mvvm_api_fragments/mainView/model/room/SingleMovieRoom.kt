package com.utad.mvvm_api_fragments.mainView.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.model.network.Genre

@Entity(tableName = "movies")
data class SingleMovieRoom (
    @PrimaryKey
    val id: Int, // No es autoincremental, el id es el de thmdb
    @ColumnInfo(name = "genre1") val genre1: String? = null,
    @ColumnInfo(name = "genre2") val genre2: String? = null,
    @ColumnInfo(name = "genre3") val genre3: String? = null,
    @ColumnInfo(name = "overview") val overview: String? = null,
    @ColumnInfo(name = "popularity") val popularity: Double = 0.0,
    @ColumnInfo(name = "poster_path") val posterPath: String? = null,
    @ColumnInfo(name = "release_date") val releaseDate: String? = null,
    @ColumnInfo(name = "title") val title: String = "",
)

fun SingleMovieRoom.toDomain(): SingleMovie{

    val listOfGenres = listOfNotNull(genre1, genre2, genre3)
        .mapIndexedNotNull { index, genre ->
            if (genre != null) Genre(index + 1, genre) else null
        }
    return SingleMovie(
        id = id,
        genres = listOfGenres,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
    )
}
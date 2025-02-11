package com.utad.mvvm_api_fragments.mainView.model.domain

import com.google.gson.annotations.SerializedName
import com.utad.mvvm_api_fragments.mainView.model.network.Genre
import com.utad.mvvm_api_fragments.mainView.model.room.SingleMovieRoom

data class SingleMovie(
    val genres: List<Genre>? = emptyList(),
    val homepage: String? = null,
    val id: Int = 0,
    val overview: String? = null,
    val popularity: Double = 0.0,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String = "",
    val voteCount: Int = 0
)

fun SingleMovie.toRoom(): SingleMovieRoom {
    return SingleMovieRoom(
        id = id,
        genre1 = genres?.getOrNull(0)?.name,
        genre2 = genres?.getOrNull(1)?.name,
        genre3 = genres?.getOrNull(2)?.name,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title
    )

}
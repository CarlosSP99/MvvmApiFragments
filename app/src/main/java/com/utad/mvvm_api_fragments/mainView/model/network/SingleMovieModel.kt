package com.utad.mvvm_api_fragments.mainView.model.network

import com.google.gson.annotations.SerializedName
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.model.room.SingleMovieRoom


data class SingleMovieModel(
    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("budget") val budget: Long = 0,
    @SerializedName("genres") val genres: List<Genre>? = emptyList(),
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("id") val id: Int = 0,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("origin_country") val originCountry: List<String>? = emptyList(),
    @SerializedName("original_language") val originalLanguage: String = "en",
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("revenue") val revenue: Long = 0,
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("title") val title: String = "",
    @SerializedName("video") val video: Boolean = false,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

fun  SingleMovieModel.toDomain(): SingleMovie {
    return SingleMovie(
        id = id,
        genres = genres,
        homepage = homepage,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteCount = voteCount)
}





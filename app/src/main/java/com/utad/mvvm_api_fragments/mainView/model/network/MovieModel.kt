package com.utad.mvvm_api_fragments.mainView.model.network

import com.google.gson.annotations.SerializedName
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie

data class MovieModel (
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
)



fun toDomain(movieModel: MovieModel) = Movie(
    adult = movieModel.adult,
    backdropPath = movieModel.backdropPath,
    genreIds = movieModel.genreIds,
    id = movieModel.id,
    originalLanguage = movieModel.originalLanguage,
    originalTitle = movieModel.originalTitle,
    overview = movieModel.overview,
    popularity = movieModel.popularity,
    posterPath = movieModel.posterPath,
    releaseDate = movieModel.releaseDate,
    title = movieModel.title,
    video = movieModel.video,
    voteAverage = movieModel.voteAverage,
    voteCount = movieModel.voteCount,
)

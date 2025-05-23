package com.ckatsidzira.data.source.remote.model

import com.ckatsidzira.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("poster_path")
    val posterPath: String,
    val title: String,
    val id: Int,
) {
    fun toDomain() = Movie(
        posterPath = posterPath,
        title = title,
        id = id,
    )
}
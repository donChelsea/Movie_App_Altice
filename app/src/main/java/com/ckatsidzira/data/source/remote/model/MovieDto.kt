package com.ckatsidzira.data.source.remote.model

import com.ckatsidzira.data.source.local.cache.model.MovieEntity
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("poster_path")
    val posterPath: String,
    val title: String,
    val id: Int,
) {
    fun toEntity(timeWindow: String) = MovieEntity(
        posterPath = posterPath,
        title = title,
        id = id,
        timeWindow = timeWindow,
    )
}
package com.ckatsidzira.data.source.remote.model

import com.ckatsidzira.data.source.local.model.CacheEntity
import com.ckatsidzira.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("poster_path")
    val posterPath: String,
    val title: String,
    val id: Int,
) {
    fun toCacheEntity(timeWindow: String) = CacheEntity(
        posterPath = posterPath,
        title = title,
        id = id,
        timeWindow = timeWindow,
    )
    fun toDomain() = Movie(
        posterPath = posterPath,
        title = title,
        id = id,
    )
}
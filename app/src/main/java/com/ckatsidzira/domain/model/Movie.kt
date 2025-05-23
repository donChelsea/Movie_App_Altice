package com.ckatsidzira.domain.model

import com.ckatsidzira.data.source.local.model.FavoritesEntity
import com.ckatsidzira.presentation.model.MovieUiModel

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
) {
    fun toUiModel() = MovieUiModel(
        posterPath = posterPath,
        title = title,
        overview = overview,
        id = id,
    )
    fun toFavoritesEntity() = FavoritesEntity(
        posterPath = posterPath,
        title = title,
        overview = overview,
        id = id,
    )
}
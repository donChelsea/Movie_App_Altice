package com.ckatsidzira.domain.model

import com.ckatsidzira.presentation.model.MovieUiModel

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
) {
    fun toUiModel() = MovieUiModel(
        posterPath = posterPath,
        title = title,
        id = id,
    )
}
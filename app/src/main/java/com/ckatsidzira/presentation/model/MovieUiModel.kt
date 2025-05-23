package com.ckatsidzira.presentation.model

import androidx.compose.runtime.Immutable
import com.ckatsidzira.domain.model.Movie

@Immutable
data class MovieUiModel(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
) {
    fun toDomain() = Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
    )
}
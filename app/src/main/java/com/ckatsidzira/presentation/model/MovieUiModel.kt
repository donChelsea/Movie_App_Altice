package com.ckatsidzira.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class MovieUiModel(
    val id: Int,
    val title: String,
    val posterPath: String,
)
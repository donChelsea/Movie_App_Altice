package com.ckatsidzira.data.source.remote.model

data class ApiResponseDto<T>(
    val page: Int,
    val results: List<T>,
)

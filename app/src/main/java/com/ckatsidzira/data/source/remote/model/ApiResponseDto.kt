package com.ckatsidzira.data.source.remote.model

data class ApiResponseDto<T>(
    val results: List<T>,
)

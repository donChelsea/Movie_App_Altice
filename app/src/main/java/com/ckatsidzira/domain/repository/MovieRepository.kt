package com.ckatsidzira.domain.repository

import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingFlow(
        timeWindow: String
    ): Flow<Resource<List<Movie>>>
}
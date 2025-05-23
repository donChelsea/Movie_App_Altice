package com.ckatsidzira.data.repository

import com.ckatsidzira.data.source.remote.MovieApi
import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.repository.MovieRepository
import com.ckatsidzira.domain.util.Resource
import com.ckatsidzira.domain.util.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override fun getTrendingFlow(
        timeWindow: String
    ): Flow<Resource<List<Movie>>> = safeFlow {
        api.getTrendingMovies(
            timeWindow = timeWindow,
            page = 1,
        ).results.map { it.toDomain() }
    }
}
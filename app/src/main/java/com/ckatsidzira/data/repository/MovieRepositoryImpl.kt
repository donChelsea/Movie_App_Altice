package com.ckatsidzira.data.repository

import com.ckatsidzira.data.source.local.cache.CacheDatabase
import com.ckatsidzira.data.source.remote.MovieApi
import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.repository.MovieRepository
import com.ckatsidzira.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    cache: CacheDatabase
) : MovieRepository {

    private val dao = cache.dao

    override fun getTrendingFlow(timeWindow: String): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

        val cachedFlow = dao.getMovies(timeWindow)
            .map { it.map { entity -> entity.toDomain() } }

        emitAll(
            cachedFlow.onEach { cachedMovies ->
                if (cachedMovies.isEmpty()) {
                    try {
                        val response = api.getTrendingMovies(timeWindow = timeWindow)
                        val entities = response.results.map { it.toEntity(timeWindow) }

                        dao.clear(timeWindow)
                        dao.insertAll(entities)
                    } catch (e: Exception) {
                        emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
                    }
                }
            }.map { movies ->
                Resource.Success(movies)
            }
        )
    }
}

package com.ckatsidzira.data.repository

import com.ckatsidzira.data.source.local.MovieDatabase
import com.ckatsidzira.data.source.remote.MovieApi
import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.repository.MovieRepository
import com.ckatsidzira.domain.util.Resource
import com.ckatsidzira.domain.util.safeCall
import com.ckatsidzira.domain.util.safeFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    db: MovieDatabase
) : MovieRepository {

    private val cacheDao = db.cacheDao
    private val favoritesDao = db.favoritesDao

    override fun getTrendingFlow(timeWindow: String): Flow<Resource<List<Movie>>> = safeFlow {
        val cachedMovies = cacheDao.getMovies(timeWindow)
            .map { list -> list.map { it.toDomain() } }
            .firstOrNull() ?: emptyList()

        cachedMovies.ifEmpty {
            val response = api.getTrendingMovies(timeWindow = timeWindow)
            val entities = response.results.map { it.toCacheEntity(timeWindow) }

            cacheDao.clear(timeWindow)
            cacheDao.insertAll(entities)

            entities.map { it.toDomain() }
        }
    }

    override fun getFavoritesFlow(): Flow<Resource<List<Movie>>> = safeFlow {
        favoritesDao.getFavoriteMovies()
            .map { list -> list.map { it.toDomain() } }
            .first()
    }

    override suspend fun addToFavorites(movie: Movie) = favoritesDao.addToFavorites(movie.toFavoritesEntity())

    override suspend fun removeFromFavorites(movie: Movie) = favoritesDao.removeFromFavorites(movie.toFavoritesEntity())

    override suspend fun isFavorite(id: Int) = favoritesDao.isFavorite(id)

    override suspend fun getMovieDetails(id: Int): Resource<Movie> = safeCall {
        api.getMovieDetails(id).toDomain()
    }
}

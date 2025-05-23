package com.ckatsidzira.domain.repository

import com.ckatsidzira.domain.model.Movie
import com.ckatsidzira.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingFlow(timeWindow: String): Flow<Resource<List<Movie>>>
    fun getFavoritesFlow(): Flow<Resource<List<Movie>>>
    suspend fun addToFavorites(movie: Movie)
    suspend fun removeFromFavorites(movie: Movie)
    suspend fun isFavorite(id: Int): Boolean
}
package com.ckatsidzira.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ckatsidzira.data.source.local.model.CacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheDao {
    @Query("SELECT * FROM cache WHERE timeWindow = :timeWindow")
    fun getMovies(timeWindow: String): Flow<List<CacheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<CacheEntity>)

    @Query("DELETE FROM cache WHERE timeWindow = :timeWindow")
    suspend fun clear(timeWindow: String)
}
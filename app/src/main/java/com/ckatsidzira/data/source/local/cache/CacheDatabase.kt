package com.ckatsidzira.data.source.local.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ckatsidzira.data.source.local.cache.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 2
)
abstract class CacheDatabase: RoomDatabase() {
    abstract val dao: CacheDao
}
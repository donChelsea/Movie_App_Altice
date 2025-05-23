package com.ckatsidzira.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ckatsidzira.data.source.local.dao.CacheDao
import com.ckatsidzira.data.source.local.dao.FavoritesDao
import com.ckatsidzira.data.source.local.model.CacheEntity
import com.ckatsidzira.data.source.local.model.FavoritesEntity

@Database(
    entities = [CacheEntity::class, FavoritesEntity::class],
    version = 4
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val cacheDao: CacheDao
    abstract val favoritesDao: FavoritesDao
}
package com.ckatsidzira.data.source.local.cache.model

import androidx.room.Entity
import com.ckatsidzira.domain.model.Movie

@Entity(
    tableName = "cache",
    primaryKeys = ["id", "timeWindow"]
)
data class MovieEntity(
    val id: Int,
    val title: String,
    val posterPath: String,
    val timeWindow: String,
) {
    fun toDomain() = Movie(
        id = id,
        title = title,
        posterPath = posterPath
    )
}
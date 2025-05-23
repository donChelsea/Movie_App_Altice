package com.ckatsidzira.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ckatsidzira.domain.model.Movie
@Entity(tableName = "favorites")
data class FavoritesEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String,
) {
    fun toDomain() = Movie(
        id = id,
        title = title,
        posterPath = posterPath,
    )
}
package com.uhi5d.spotibud.domain.model.genres

import androidx.room.Entity

@Entity(tableName = "table_genres")
data class Genres(
    val genres: List<String>?
)
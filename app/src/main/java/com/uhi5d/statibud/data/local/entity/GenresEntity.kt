package com.uhi5d.statibud.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uhi5d.statibud.domain.model.genres.Genres

@Entity(tableName = "table_genres")
data class GenresEntity(
    @PrimaryKey(autoGenerate = true)
    val tId: Int = 0,

    val genre: String
)

fun Genres.asGenresEntity() = this.genres.map {
    GenresEntity(genre = it)
}
fun List<GenresEntity>.asGenres(): Genres {
    val list = mutableListOf<String>()
    this.forEach { list.add(it.genre) }
    return Genres(list)
}

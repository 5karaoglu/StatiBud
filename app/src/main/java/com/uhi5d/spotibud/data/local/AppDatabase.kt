package com.uhi5d.spotibud.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uhi5d.spotibud.domain.model.genres.Genres

@Database(
    entities = [Genres::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun genresDao(): GenreDao
}

package com.uhi5d.statibud.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uhi5d.statibud.data.local.entity.GenresEntity


@Database(
    entities = [GenresEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun genresDao(): GenreDao
}

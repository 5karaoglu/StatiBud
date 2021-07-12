package com.uhi5d.spotibud.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uhi5d.spotibud.data.local.entity.GenresEntity

@Dao
interface GenreDao {

    @Query("SELECT * FROM table_genres")
    suspend fun getAllGenres(): List<GenresEntity>

    @Query("DELETE FROM table_genres")
    suspend fun clearGenres()

    @Insert
    suspend fun saveGenre(list: List<GenresEntity>)


}
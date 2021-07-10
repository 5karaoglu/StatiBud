package com.uhi5d.spotibud.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uhi5d.spotibud.domain.model.genres.Genres

@Dao
interface GenreDao {

    @Query("SELECT * FROM table_genres")
    suspend fun getAllGenres(): Genres

    @Query("DELETE FROM table_genres")
    suspend fun clearGenres()

    @Insert
    suspend fun saveGenre(genres:Genres): Int


}
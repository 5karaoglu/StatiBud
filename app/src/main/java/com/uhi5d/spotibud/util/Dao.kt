package com.uhi5d.spotibud.util

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.uhi5d.spotibud.model.SearchHistory

@Dao
interface Dao {
    @Query("SELECT * FROM searchHistory")
    fun getAll(): List<SearchHistory>

    @Insert
    fun insert(searchHistory: SearchHistory)

    @Delete
    fun delete(searchHistory: SearchHistory)
}
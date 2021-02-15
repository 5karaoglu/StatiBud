package com.example.contestifyfirsttry.util

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.contestifyfirsttry.model.SearchHistory

@Dao
interface Dao {
    @Query("SELECT * FROM searchHistory")
    fun getAll(): List<SearchHistory>

    @Insert
    fun insert(searchHistory: SearchHistory)

    @Delete
    fun delete(searchHistory: SearchHistory)
}
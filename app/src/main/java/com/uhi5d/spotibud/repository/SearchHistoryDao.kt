package com.uhi5d.spotibud.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.uhi5d.spotibud.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM searchHistory")
    fun getAllSH(): Flow<List<SearchHistory>>

    @Insert
    fun insertSH(searchHistory: SearchHistory): Long

    @Delete
    fun deleteSH(searchHistory: SearchHistory): Int

}
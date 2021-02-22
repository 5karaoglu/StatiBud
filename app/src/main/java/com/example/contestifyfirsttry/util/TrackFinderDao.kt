package com.example.contestifyfirsttry.util

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.contestifyfirsttry.model.SearchHistory
import com.example.contestifyfirsttry.model.TrackFinderTracks

@Dao
interface TrackFinderDao {
    @Query("SELECT * FROM trackfindertracks")
    fun getAll(): List<TrackFinderTracks>

    @Insert
    fun insert(trackFinderTracks: TrackFinderTracks)

    @Query("DELETE FROM trackfindertracks")
    fun deleteAll()
}
package com.uhi5d.spotibud.util

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uhi5d.spotibud.model.TrackFinderTracks

@Dao
interface TrackFinderDao {
    @Query("SELECT * FROM trackfindertracks")
    fun getAll(): List<TrackFinderTracks>

    @Insert
    fun insert(trackFinderTracks: TrackFinderTracks)

    @Query("DELETE FROM trackfindertracks")
    fun deleteAll()
}
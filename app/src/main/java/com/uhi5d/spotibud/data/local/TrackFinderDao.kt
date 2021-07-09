package com.uhi5d.spotibud.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uhi5d.spotibud.model.TrackFinderTracks
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackFinderDao {
    @Query("SELECT * FROM trackfindertracks")
    fun getAllTFT(): Flow<List<TrackFinderTracks>>

    @Insert
    fun insertTFT(trackFinderTracks: TrackFinderTracks): Long

    @Query("DELETE FROM trackfindertracks")
    fun deleteAllTFT()
}
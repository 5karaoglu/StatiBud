package com.example.contestifyfirsttry.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackFinderTracks(
    @PrimaryKey (autoGenerate = true) var uid: Int? = 0,
    @ColumnInfo(name = "trackId") var trackId: String?,
    @ColumnInfo(name = "trackName") var trackName: String?,
    @ColumnInfo(name = "artistId") var artistId: String?,
    @ColumnInfo(name = "artistName") var artistName: String?,
    @ColumnInfo(name = "albumImage") var albumImage: String?
)

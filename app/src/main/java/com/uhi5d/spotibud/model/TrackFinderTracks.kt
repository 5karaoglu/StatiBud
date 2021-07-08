package com.uhi5d.spotibud.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TrackFinderTracks(
    @PrimaryKey(autoGenerate = true) var uid: Int? = 0,
    @ColumnInfo(name = "trackId") var trackId: String?,
    @ColumnInfo(name = "trackName") var trackName: String?,
    @ColumnInfo(name = "artistId") var artistId: String?,
    @ColumnInfo(name = "artistName") var artistName: String?,
    @ColumnInfo(name = "albumImage") var albumImage: String?
) : Parcelable

package com.example.contestifyfirsttry.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(
    @PrimaryKey (autoGenerate = true) var uid: Int? = 0,
    @ColumnInfo (name = "type") var type: String?,
    @ColumnInfo (name = "aid") var aid: String?,
    @ColumnInfo (name = "name") var name: String?,
    @ColumnInfo (name = "artistId") var artistId: String?,
    @ColumnInfo (name = "aimage") var aImage: String?
)

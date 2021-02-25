package com.uhi5d.spotibud.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(
    @PrimaryKey (autoGenerate = true) var uid: Int? = 0,
    @ColumnInfo (name = "type") var type: String?,
    @ColumnInfo (name = "sId") var sId: String?,
    @ColumnInfo (name = "name") var name: String?,
    @ColumnInfo (name = "artistId") var artistId: String?,
    @ColumnInfo (name = "artistName") var artistName: String?,
    @ColumnInfo (name = "cImage") var cImage: String?
)

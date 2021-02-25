package com.uhi5d.spotibud.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres") val genres: List<String>
)

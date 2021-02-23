package com.example.contestifyfirsttry.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres") val genres: List<String>
)

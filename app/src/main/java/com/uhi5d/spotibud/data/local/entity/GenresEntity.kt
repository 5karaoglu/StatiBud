package com.uhi5d.spotibud.data.local.entity

import com.uhi5d.spotibud.domain.model.genres.Genres

fun List<String>.asGenres() =
    Genres(this)

fun Genres.asList() =
    this.genres!!